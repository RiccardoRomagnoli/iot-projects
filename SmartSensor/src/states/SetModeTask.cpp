#include "Arduino.h"
#include "SetModeTask.h"
#include "Task.h"

SetModeTask::SetModeTask(Task* singleTask, Task* manualTask, Task* autoTask, Button* singleButtin, Button* manualButton, Button* autoButton){
  this->singleTask = singleTask;
  this->manualTask = manualTask;
  this->autoTask = autoTask;
  this->singleButton = singleButtin;
  this->manualButton = manualButton;
  this->autoButton = autoButton;
}
  
void SetModeTask::init(int period){
  Task::init(period);
  this->init();
}

void SetModeTask::init(){
  this->currentModeTask = manualTask;
}
  
void SetModeTask::tick(){
  if(singleButton->isPressed() && currentModeTask != singleTask){
    currentModeTask->setActive(false);
    currentModeTask = singleTask;
    currentModeTask->setActive(true);
    currentModeTask->init();
  }else if(manualButton->isPressed() && currentModeTask != manualTask){
    currentModeTask->setActive(false);
    currentModeTask = manualTask;
    currentModeTask->setActive(true);
    currentModeTask->init();
  }else if(autoButton->isPressed() && currentModeTask != autoTask){
    currentModeTask->setActive(false);
    currentModeTask = autoTask;
    currentModeTask->setActive(true);
    currentModeTask->init();
  }
}