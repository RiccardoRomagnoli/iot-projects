#include "Arduino.h"
#include "led/Led.h"
#include "potenz/Potenziometro.h"
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>
#include <ArduinoJson.h>

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
String address = "http://194aa522.ngrok.io";
/*STATO BIDONE */
enum { AVAILABLE, NOTAVAILABLE} stato;
/*Wmax*/
int maxWeight = 0;
int pesoAttuale = 0;

void setup() {                
  Serial.begin(9600);

  ledOk = new Led(LEDOK_PIN);
  ledErr = new Led(LEDERR_PIN);
  pot = new Potenziometro(POT_PIN);

  maxWeight = pot->readPotenziometro() * 1000;
  stato = AVAILABLE;

  ledOk->switchOn();
  ledErr->switchOff();
  
  WiFi.begin(ssidName, pwd);
  Serial.print("Connecting...");
  while (WiFi.status() != WL_CONNECTED) {  
    delay(500);
    Serial.print(".");
  } 
  Serial.println("Connected");
}

String extractJSONfromArray(String risposta){

  // compute the required size
  const size_t CAPACITY = JSON_ARRAY_SIZE(100);

  // allocate the memory for the document
  StaticJsonDocument<CAPACITY> doc;

  // parse a JSON array
  deserializeJson(doc, risposta);

  // extract the values
  JsonArray array = doc.as<JsonArray>();
  String valore;
  for(JsonVariant v : array) {
      valore = v.as<String>();
  }
  return valore;
}

int sendData(String address, String apiMethod, String key, String value){
  int httpCode = 0;
  if(WiFi.status()== WL_CONNECTED){   //Check WiFi connection status
    WiFiClient client;
    HTTPClient http;    //Declare object of class HTTPClient
  
    http.begin(client, address + "/api/" + apiMethod);      //Specify request destination
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
    WiFiClient client;
    HTTPClient http;  //Declare an object of class HTTPClient

    http.begin(client, address + "/api/" + apiMethod);  //Specify request destination
    int httpCode = http.GET(); //Send the request
    if (httpCode > 0) { //Check the returning code
      payload = http.getString();   //Get the request response payload
    }
  
    http.end();   //Close connection
  }
  return payload;
}

bool isAvailable(){
  String dati = getData(address, "isAvailable");
  String risposta = extractJSONfromArray(dati);
  const size_t CAPACITY = JSON_OBJECT_SIZE(4);
  StaticJsonDocument<CAPACITY> doc;
  // deserialize the object
  deserializeJson(doc, risposta);

  // extract the data
  JsonObject object = doc.as<JsonObject>();
  String stato = object["value"];
  Serial.println(stato);

  return stato == "true" ? true : false; 
}

int setAvailable(String stato){
  return sendData(address, "setavailability", "value", stato);
}

int getTotalWeight(){
  String dati = getData(address, "ndeposit");
  String risposta = extractJSONfromArray(dati);
  const size_t CAPACITY = JSON_OBJECT_SIZE(1);
  StaticJsonDocument<CAPACITY> doc;

  // deserialize the object
  deserializeJson(doc, risposta);

  // extract the data
  JsonObject object = doc.as<JsonObject>();
  const int peso = object["weight"];
  
  return peso;
}

void checkStato(){
  switch(stato){
    case AVAILABLE:
      if(!maxWeight){
        maxWeight = pot->readPotenziometro();
        ledOk->switchOn();
        ledErr->switchOff();
      }  
    break;
    case NOTAVAILABLE:
      if(maxWeight){
        maxWeight = 0;
        ledOk->switchOff();
        ledErr->switchOn();
      }
    break;
  }
}
void loop() {
  if(stato == AVAILABLE && !isAvailable()) {
    stato = NOTAVAILABLE;
    checkStato();
  } else if(stato == NOTAVAILABLE && isAvailable()) {
    stato = AVAILABLE;
    checkStato();
  } else if(stato == AVAILABLE && pesoAttuale > maxWeight){
    stato = NOTAVAILABLE;
    setAvailable("false");
    checkStato();
  } else if(stato == AVAILABLE && pesoAttuale <= maxWeight){
    pesoAttuale = getTotalWeight();
    Serial.println(pesoAttuale);
  }
  delay(1000);
}
