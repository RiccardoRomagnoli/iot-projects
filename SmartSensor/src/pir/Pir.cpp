#include "Pir.h"
#include "Arduino.h"

Pir::Pir(int pin){
  this->pin = pin;
  pinMode(pin,INPUT);
}

bool Pir::checkPirMovement(){
  return digitalRead(pin) == HIGH;
}
