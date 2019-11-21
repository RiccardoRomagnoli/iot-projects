#include "AutoTask.h"
#include "Arduino.h"

#define OFFSET (ANGLE / 2)

//Gestione allarme
//scan oraria e anti oraria
//raccolta dati, invio dati
//spegnere servo al cambio modalità

AutoTask::AutoTask(Task* blinkTask, Sonar* sonar, ServoMotor* servo, SharedState* shared){
  this->blinkTask = blinkTask;
  this->sonar = sonar;
  this->servo = servo;
  this->shared = shared;
}
  
void AutoTask::init(int period){
  Task::init(period);
  this->init();
}

void AutoTask::init(){
  state = SCANNING;
  actualPosition = 0;
  clockwise = true;
  servo->on();
}
  
void AutoTask::tick(){
  Serial.println("Modalita auto attiva");
  switch (state)
  {
  case SCANNING:
    this->distance = sonar->sonarScan();
    if(distance >= D_MIN && distance <= D_MAX){
      //Object detected
      state = ALARM;
      //led on
    }else if (distance > D_MAX){
      //Object not detected
      distance = 0;
      if(blinkTask->isActive()){
        state = ALARM;
      }else{
        state = MOVE;
      }
    }else if (distance < D_MIN){
      //Alarm With Trackings
      state = TRACKING;
    }
    timeElapsed = this->getPeriod();
    break;
  case ALARM:
    if(!blinkTask->isActive()){
      alarmClockwise = clockwise;
      blinkTask->setActive(true);
    }
    state = MOVE;
    timeElapsed += this->getPeriod();
    break;
  case MOVE:
    if(clockwise)
      ++actualPosition;
    else
      --actualPosition;
    servo->setPosition(ANGLE * actualPosition + OFFSET);
    if(actualPosition == -1 || actualPosition == POSITIONS){
      clockwise = !clockwise;
      if(blinkTask->isActive() && clockwise == alarmClockwise )
        blinkTask->setActive(false);
    }
    state = SEND;
    timeElapsed += this->getPeriod();
    break;
  case TRACKING:
    state = SEND;
    timeElapsed += this->getPeriod();
    break;
  case SEND:
    //msg send serial distance:ANGLE * actualPosition + OFFSET
    state = IDLE;
    timeElapsed += this->getPeriod();
    break;
  case IDLE:
    if(timeElapsed < shared->getTimeOfCicle())
      timeElapsed += this->getPeriod();
    else
      state = SCANNING;
    break;
  }
}