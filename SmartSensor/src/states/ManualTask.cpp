#include "ManualTask.h"
#include "Arduino.h"
#include "serial/GUI.h"

ManualTask::ManualTask(Sonar* sonar, ServoMotor* servo, GUI* gui){
  this->sonar = sonar;
  this->servo = servo;
  this->gui = gui;
}
  
void ManualTask::init(int period){
  Task::init(period);
  this->init();
}

void ManualTask::init(){
  state = RECEIVING;
  servo->on();
  servo->setPosition(MANUAL_DEFAULT_POSITION);
}

void ManualTask::stop() {
  servo->off();
}
  
void ManualTask::tick(){
  switch (state)
  {
    case RECEIVING:{
      this->angle = gui->getAngle();
      if(angle != -1){
        state = MOVE;
      }
      break;
    }
    case MOVE:{
      servo->setPosition(angle);
      state = SCAN;
      break;
    } 
    case SCAN:{
      float distance = sonar->sonarScan();
      gui->sendScan(angle, distance);
      state = RECEIVING;
      break;
    }
  }
}