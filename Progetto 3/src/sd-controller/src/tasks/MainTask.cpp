#include "MainTask.h"
#include "Arduino.h"
#include "communication/GUI.h"

#define SEC 1000
#define TIMEOUT_TIME_SEC 15

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
      timeElapsed=0;
      timerSec = 15;
      Type selectedType = gui->getType();
      switch(selectedType){
        case NONE:
          break;
        case A_TYPE:
          ledA->switchOn();
          servo->open();
          state = TIMING;
          break;
        case B_TYPE:
          ledB->switchOn();
          servo->open();
          state = TIMING;
          break;
        case C_TYPE:
          ledC->switchOn();
          servo->open();
          state = TIMING;
          break;
      }
      break;
    }
    case TIMING:{
      if(timeElapsed >= SEC){
        gui->sendTime(--timerSec);
        timeElapsed=0;
      }
      if(timerSec==0){
        state = TIMEOUT;
      }else{
        if(gui->checkDeposit()){
          state = ACCEPTING;
          ledA->switchOff();
          ledB->switchOff();
          ledC->switchOff();
          servo->close();
          gui->sendConfirm();
        }
        if(gui->checkExtend()){
          timerSec = TIMEOUT_TIME_SEC;
          timeElapsed=0;
        }
        if(gui->checkBack()){
          timeElapsed=0;
          state = TIMEOUT;
        }
      }
      timeElapsed+=this->getPeriod();
      break;
    }
    case TIMEOUT:{
      state = ACCEPTING;
      ledA->switchOff();
      ledB->switchOff();
      ledC->switchOff();
      servo->close();
      break;
    }
    case IDLE:{
      state = ACCEPTING;
      break;
    }
  }
}