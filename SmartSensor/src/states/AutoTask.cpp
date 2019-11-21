#include "AutoTask.h"
#include "Arduino.h"

#define OFFSET (ANGLE / 2)

//Gestione allarme
//scan oraria e anti oraria
//raccolta dati, invio dati
//spegnere servo al cambio modalità

AutoTask::AutoTask(Task* blinkTask){
  this->blinkTask = blinkTask;
  this->sonar = new Sonar(TRIGSONAR, ECHOSONAR);
  this->servo = new ServoMotorImpl(SERVOMOTOR);
}
  
void AutoTask::init(int period){
  Task::init(period);
  this->init();
}

void AutoTask::init(){
  for(int i = 0; i < POSITIONS; i++){
    results[i] = 0;
  }
  actualPosition = 0;
  clockwise = true;
  servo->on();
}
  
void AutoTask::tick(){
  Serial.println("Modalita auto attiva");
  checkObject();
  servo->setPosition(ANGLE * actualPosition + OFFSET);
  clockwise ? ++actualPosition : --actualPosition;
  checkEndScan();
}

void AutoTask::setAlarm(bool status){
  alarm = status;
  alarmClockwise = clockwise;
  this->blinkTask->setActive(status);
}

void AutoTask::checkEndScan(){
  if(actualPosition == -1 || actualPosition == POSITIONS){
    clockwise = !clockwise;
  }
  if(alarm && clockwise == alarmClockwise )
    this->setAlarm(false);
}

void AutoTask::checkObject(){
  float distance = sonar->sonarScan(); 

  if(distance >= D_MIN && distance <= D_MAX){
    //Object detected
    this->setAlarm(true);
    this->results[actualPosition] = distance;
  }else if (distance > D_MAX){
    //Object not detected
  }else if (distance < D_MIN){
    //Alarm With Tracking
    alarmTracking = true;
    this->results[actualPosition] = distance;
  }
}