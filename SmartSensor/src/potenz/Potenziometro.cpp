#include "Potenziometro.h"
#include "Arduino.h"

Potenziometro::Potenziometro(int pin){
  this->pin = pin;
  }

int Potenziometro::readPotenziometro(){
  return analogRead(pin);
};
