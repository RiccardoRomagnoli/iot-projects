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
String ssidName = "FASTWEB-enzo-2,4";
/* WPA2 PSK password */
String pwd = "casaenzo2017";
/* service IP address */ 
String address = "https://194aa522.ngrok.io";
/*STATO BIDONE */
String stato = "available";
/*Wmax*/
int maxWeight = 0;

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

int sendData(String address, String apiMethod, String key, String value){
  int httpCode = 0;
  if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
  
    HTTPClient http;    //Declare object of class HTTPClient
  
    http.begin(address + "/api/" + apiMethod);      //Specify request destination
    http.addHeader("Content-Type", "application/json");  //Specify content-type header
  
    String msg = "{ \"" + key + "\":\"" + value + "\" }";
    httpCode = http.POST(msg);   //Send the request
    String payload = http.getString();                  //Get the response payload
  
    Serial.println(httpCode);   //Print HTTP return code
    Serial.println(payload);    //Print request response payload
  
    http.end();  //Close connection
  } 
    
  return httpCode;
}

String getData(String address, String apiMethod){  
  String payload = "Errore";
  if (WiFi.status() == WL_CONNECTED) { //Check WiFi connection status
  
    HTTPClient http;  //Declare an object of class HTTPClient
  
    http.begin(address + "/api/" + apiMethod);  //Specify request destination
    int httpCode = http.GET(); //Send the request
  
    if (httpCode > 0) { //Check the returning code
  
      payload = http.getString();   //Get the request response payload
      Serial.println(payload);             //Print the response payload

    }
  
    http.end();   //Close connection
  }
  return payload;
}

String isAvailable(){
  return getData(address, "isAvailable");
}

int setAvailable(bool stato){
  return sendData(address, "setavailability", "value", stato);
}

String getTotalWeight(){
  String dati = getData(address, "ndeposit");
  Serial.println(dati);
  return dati;
}

void checkStato(){
  switch(stato){
    case "available":
      if(!maxWeight){
        maxWeight = pot->readPotenziometro();
        ledOk->switchOn();
        ledErr->switchOff();
      }  
    break;
    case "not-available":
      if(maxWeight){
        maxWeight = 0;
        ledOk->switchOff();
        ledErr->switchOn();
      }
    break;
  }
}
void loop() {

  //check peso totale e switch stato
  int pesoAttuale = getTotalWeight();
  if(stato == "available" && pesoAttuale > maxWeight){
    stato = "not-available";
    setAvailable(false);
    checkStato();

  } else if(stato == "not-available" && isAvailable()) {
    stato = "available";
    checkStato();
  }
}
