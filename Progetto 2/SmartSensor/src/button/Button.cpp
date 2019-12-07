#include "Button.h"
#include "Arduino.h"

Button::Button(int pin){
  this->pin = pin;
  pinMode(pin,INPUT);
}

bool Button::isPressed(){
  if(digitalRead(pin) == HIGH)
    return true;
  return false;
}