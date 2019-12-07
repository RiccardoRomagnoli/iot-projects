#include "Potenziometro.h"
#include "Arduino.h"

Potenziometro::Potenziometro(int pin){
  this->pin = pin;
  }

// Return positions from 2 to 10 multiple of 2
int Potenziometro::readPotenziometro(){
  return map(analogRead(pin), 0, 1023, 1, 5) * 2;
};
