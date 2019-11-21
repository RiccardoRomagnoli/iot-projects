#include "BlinkTask.h"

BlinkTask::BlinkTask(int pin){
  this->pin = pin;    
}
  
void BlinkTask::init(int period){
  Task::init(period);
  led = new Led(pin); 
  state = OFF;    
}

void BlinkTask::init(){
  this->state = OFF;
}

void BlinkTask::setActive(bool active){
  active ? this->led->switchOn() : this->led->switchOff();
  Task::setActive(active);
}



void BlinkTask::tick(){
  switch (state){
    case OFF:
      led->switchOn();
      state = ON; 
      break;
    case ON:
      led->switchOff();
      state = OFF;
      break;
  }
}
