#include "Button.h"
#include "Arduino.h"

Button::Button(int pin){
  this->pin = pin;
  pinMode(pin,INPUT);
}

int Button::readButton(){
  return digitalRead(pin);
}

bool Button::checkPressed(){
  if(readButton() == HIGH)
    return true;
  return false;
}