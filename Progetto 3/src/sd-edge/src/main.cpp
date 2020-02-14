#include "Arduino.h"
#include "led/Led.h"
#include "potenz/Potenziometro.h"
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>

#define LEDERR_PIN D4
#define LEDOK_PIN D3
#define POT_PIN A0

Potenziometro* pot;
Light* ledErr;
Light* ledOk;

/* wifi network name */
String ssidName = "littlebarfly";
/* WPA2 PSK password */
String pwd = "seiot1920";
/* service IP address */ 
String address = "http://0dfeb236.ngrok.io";

void setup() {                
  Serial.begin(9600);
  ledOk = new Led(LEDOK_PIN);
  ledErr = new Led(LEDERR_PIN);
  pot = new Potenziometro(POT_PIN);
  
  ledOk->switchOff();
  ledErr->switchOff();

  WiFi.begin(ssidName, pwd);
  Serial.print("Connecting...");
  while (WiFi.status() != WL_CONNECTED) {  
    delay(500);
    Serial.print(".");
  } 
  Serial.println("Connected: \n local IP: "+WiFi.localIP());
}

int sendData(String address, float value, String place){  
   HTTPClient http;    
   http.begin(address + "/api/data");      
   http.addHeader("Content-Type", "application/json");     
   String msg = 
    String("{ \"value\": ") + String(value) + ", \"place\": \"" + place +"\" }";
   int retCode = http.POST(msg);   
   http.end();
      
   // String payload = http.getString();  
   // Serial.println(payload);      
   return retCode;
}

void loop() {
  int value = pot->readPotenziometro();
  Serial.println("Value: " + String(value));
  ledOk->switchOn();
  Serial.println("ON");
  delay(500);             
  ledOk->switchOff();
  Serial.println("OFF");   
  delay(500);

  if (WiFi.status()== WL_CONNECTED){
    /* send data */
    Serial.println("sending "+String(value)+"...");
    int code = sendData(address, value, "home");

    /* log result */
    if (code == 200){
      Serial.println("ok");
    } else {
      Serial.println("error");
    }
  } else { 
    Serial.println("Error in WiFi connection");   
  }    
}
