#include "AutoTask.h"
#include "Arduino.h"

#define OFFSET (ANGLE / 2)

//Gestione allarme
//scan oraria e anti oraria
//raccolta dati, invio dati
//spegnere servo al cambio modalità

AutoTask::AutoTask(Task* blinkTask, Sonar* sonar, ServoMotor* servo){
  this->blinkTask = blinkTask;
  this->sonar = sonar;
  this->servo = servo;
}
  
void AutoTask::init(int period){
  Task::init(period);
  this->init();
}

void AutoTask::init(){
  actualPosition = 0;
  clockwise = true;
  servo->on();
}
  
void AutoTask::tick(){
  Serial.println("Modalita auto attiva");
  float distance = checkObject();
  //serial.sendDistanceAngle(distance, actualPosition * ANGLE + OFFSET);
  servo->setPosition(ANGLE * actualPosition + OFFSET);
  clockwise ? ++actualPosition : --actualPosition;
  checkEndScan();
}

void AutoTask::checkEndScan(){
  if(actualPosition == -1 || actualPosition == POSITIONS){
    clockwise = !clockwise;
  }
  if(alarm && clockwise == alarmClockwise )
    this->setAlarm(false);
}

float AutoTask::checkObject(){
  float distance = sonar->sonarScan(); 

  if(distance >= D_MIN && distance <= D_MAX){
    //Object detected
    this->setAlarm(true);
  }else if (distance > D_MAX){
    distance = 0;
    //Object not detected
  }else if (distance < D_MIN){
    //Alarm With Tracking
    alarmTracking = true;
  }
  return distance;
}

void AutoTask::setAlarm(bool status){
  alarm = status;
  alarmClockwise = clockwise;
  this->blinkTask->setActive(status);
}