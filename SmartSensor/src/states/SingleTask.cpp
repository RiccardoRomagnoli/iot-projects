#include "SingleTask.h"
#include "Arduino.h"


SingleTask::SingleTask(Task* blinkTask, Pir* pir, Sonar* sonar, ServoMotor* servo){
  this->blinkTask = blinkTask;
  this->pir = pir;
  this->sonar = sonar;
  this->servo = servo;
}

void SingleTask::init(int period){
  Task::init(period);
  this->init();
}

void SingleTask::init(){
  attivo = false;
  for(int i = 0; i < POSITIONS; i++){
    results[i] = 0;
  }
  actualPosition = 0;
  directionOrario = true;
}

void SingleTask::tick(){
  blinkTask->setActive(false);
  if(attivo || pir->checkPirMovement()) {
   attivo = true;
   results[actualPosition] = sonar->sonarScan(); 
   if(results[actualPosition] >= 0.2 && results[actualPosition] <= 0.4){
     blinkTask->setActive(true);
   }
   directionOrario ? actualPosition++ : actualPosition--;
   if(actualPosition == 16 || actualPosition == -1){
     attivo = false;
     directionOrario = !directionOrario;
     actualPosition += -(actualPosition % (POSITIONS - 1));
     servo->off();
     //invia dati
   } else {
    servo->on();
    servo->setPosition(ANGLE * actualPosition + (ANGLE / 2)); 
   }
  } else {
    //risparmio energetico
  }
  
}