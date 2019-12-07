#include "BlinkTask.h"

BlinkTask::BlinkTask(Light* led){
  this->led = led;
}
  
void BlinkTask::init(int period){
  Task::init(period);
  this->state = OFF;    
}

void BlinkTask::init(){
  this->state = OFF;
}

void BlinkTask::setActive(bool active){
//   active ? this->led->switchOn() : this->led->switchOff();
//   active ? this->state = OFF : this->state = ON;
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
