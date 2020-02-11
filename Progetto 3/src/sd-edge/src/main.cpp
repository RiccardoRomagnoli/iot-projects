#include "user_config.h"
#include "Arduino.h"

#define LEDERR_PIN 3
#define LEDOK_PIN 4
#define POT_PIN A0

void setup() {                
  pinMode(LEDOK_PIN, OUTPUT); 
  Serial.begin(115200);     
  Serial.println("READY"); 
}

void loop() {
  int value = analogRead(POT_PIN);
  Serial.println("Value: " + String(value));
  digitalWrite(LEDOK_PIN, HIGH);
  Serial.println("ON");
  delay(500);             
  digitalWrite(LEDOK_PIN, LOW);
  Serial.println("OFF");   
  delay(500);              
}