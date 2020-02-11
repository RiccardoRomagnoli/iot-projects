#include "MainTask.h"
#include "Arduino.h"

#define SEC 1000

MainTask::MainTask(ServoMotor* servo, Light* ledA, Light* ledB, Light* ledC, GUI* gui){
  this->ledA = ledA;
  this->ledB = ledB;
  this->ledC = ledC;
  this->servo = servo;
  this->gui = gui;
}
  
void MainTask::init(int period){
  Task::init(period);
}

void MainTask::init(){
  state = ACCEPTING;
  servo->on();
}

void MainTask::stop(){
  servo->off();
}
  
void MainTask::tick(){
  switch (state)
  {
    case ACCEPTING:{
      ledA->switchOn();
      ledC->switchOff();
      state = TIMING;
      break;
    }
    case TIMING:{
      ledB->switchOn();
      state = TIMEOUT;
      break;
    }
    case TIMEOUT:{
      ledC->switchOn();
      ledA->switchOff();
      state = IDLE;
      break;
    }
    case IDLE:{
      state = ACCEPTING;
      break;
    }
  }
}