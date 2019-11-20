#include "Arduino.h"
#include "SetModeTask.h"
#include "Task.h"

SetModeTask::SetModeTask(Task* singleTask, Task* manualTask, Task* autoTask){
  this->singleTask = singleTask;
  this->manualTask = manualTask;
  this->autoTask = autoTask;
  this->singleButton = new Button(BUTTON_SINGLE);
  this->manualButton = new Button(BUTTON_MANUAL);
  this->autoButton = new Button(BUTTON_AUTO);
}
  
void SetModeTask::init(int period){
  Task::init(period);
  this->init();
}

void SetModeTask::init(){
  this->currentModeTask = manualTask;
}
  
void SetModeTask::tick(){
  checkButtonPressed();
  checkSerialReceived();
}

void SetModeTask::checkButtonPressed(){
  if(singleButton->checkPressed() && currentModeTask != singleTask){
    currentModeTask->setActive(false);
    currentModeTask = singleTask;
    currentModeTask->setActive(true);
    currentModeTask->init();
  }else if(manualButton->checkPressed() && currentModeTask != manualTask){
    currentModeTask->setActive(false);
    currentModeTask = manualTask;
    currentModeTask->setActive(true);
    currentModeTask->init();
  }else if(autoButton->checkPressed() && currentModeTask != autoTask){
    currentModeTask->setActive(false);
    currentModeTask = autoTask;
    currentModeTask->setActive(true);
    currentModeTask->init();
  }
}

void SetModeTask::checkSerialReceived(){
  
}