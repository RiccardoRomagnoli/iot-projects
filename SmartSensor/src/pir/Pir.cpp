#include "Pir.h"
#include "Arduino.h"

Pir::Pir(int pin){
  this->pin = pin;
  pinMode(pin,INPUT);
  calibratePir();
}

void Pir::calibratePir(){
  Serial.println("Calibrazione pir");
  delay(TIME_TO_CALIBRATE);
  Serial.println("Pir ready");
}

bool Pir::checkPirMovement(){
  return readPir() ? true : false;
}

int Pir::readPir(){
  return digitalRead(pin);
};
