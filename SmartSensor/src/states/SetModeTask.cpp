#include "Arduino.h"
#include "SetModeTask.h"
#include "Task.h"

SetModeTask::SetModeTask(Task* singleTask, Task* manualTask, Task* autoTask,
 Button* singleButton, Button* manualButton, Button* autoButton,
 Potenziometro* pot, GUI* gui, SharedState* sharedState){
  this->singleTask = singleTask;
  this->manualTask = manualTask;
  this->autoTask = autoTask;
  this->singleButton = singleButton;
  this->manualButton = manualButton;
  this->autoButton = autoButton;
  this->pot = pot;
  this->gui = gui;
  this->sharedState = sharedState;
}
  
void SetModeTask::init(int period){
  Task::init(period);
  sharedState->setTempTimeOfCicleByPot(pot->readPotenziometro() * 1000 / POSITIONS);
}

void SetModeTask::init(){
  state = MANUAL;

}

void SetModeTask::stop(){
}
  
void SetModeTask::tick(){
  switch (state){
    case SINGLE:{
      if(manualButton->isPressed() || gui->checkManual()){
        state = MANUAL;
        singleTask->setActive(false);
        manualTask->setActive(true);
        gui->sendCurrentMode("m");
      }
      if(autoButton->isPressed() || gui->checkAuto()){
        state = AUTO;
        singleTask->setActive(false);
        autoTask->setActive(true);
        gui->sendCurrentMode("a");
      }
      sharedState->setTempTimeOfCicleByPot(pot->readPotenziometro() * 1000 / POSITIONS);
      if(gui->getSpeed() != -1)
        sharedState->setTempTimeOfCicle(gui->getSpeed() * 1000 / POSITIONS);
      break;    
    }
    
    case AUTO:{
      if(manualButton->isPressed() || gui->checkManual()){
        state = MANUAL;
        autoTask->setActive(false);
        manualTask->setActive(true);
        gui->sendCurrentMode("m");
      }
      if(singleButton->isPressed() || gui->checkSingle()){
        state = SINGLE;
        autoTask->setActive(false);
        singleTask->setActive(true);
        gui->sendCurrentMode("s");
      }
      sharedState->setTempTimeOfCicleByPot(pot->readPotenziometro() * 1000 / POSITIONS);
      if(gui->getSpeed() != -1)
        sharedState->setTempTimeOfCicle(gui->getSpeed() * 1000 / POSITIONS);
      break;    
    }

    case MANUAL:{
      if(singleButton->isPressed() || gui->checkSingle()){
        state = SINGLE;
        manualTask->setActive(false);
        singleTask->setActive(true);
        gui->sendCurrentMode("s");
      }
      if(autoButton->isPressed() || gui->checkAuto()){
        state = AUTO;
        manualTask->setActive(false);
        autoTask->setActive(true);
        gui->sendCurrentMode("a");
      }
      break;    
    }
  }
}