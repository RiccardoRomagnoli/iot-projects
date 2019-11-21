#include "SingleTask.h"
#include "Arduino.h"
#include "avr/sleep.h"

void wakeUp(){}

SingleTask::SingleTask(Task* blinkTask, Pir* pir, Sonar* sonar, ServoMotor* servo, Light* led_d, SharedState* shared){
  this->shared = shared;
  this->blinkTask = blinkTask;
  this->pir = pir;
  this->sonar = sonar;
  this->servo = servo;
  this->led_d = led_d;
}

void SingleTask::init(int period){
  Task::init(period);
  this->init();

}

void SingleTask::init(){
  attachInterrupt(digitalPinToInterrupt(2), wakeUp, RISING);
  actualPosition = 0;
  nStateDone = 0;
  state = STANDBY;
  directionOrario = true;
}

void SingleTask::tick(){

  switch (state) {
    case STANDBY:{
      Serial.println("standby");
      set_sleep_mode(SLEEP_MODE_PWR_DOWN);  
      sleep_enable();
      sleep_mode();  

      bool det = true; // pir attivato
      if (det) {
        Serial.println("sveglio");
        state = SCAN;
        nStateDone = 0;
      }
      break;
    }

    case SCAN:{
      //servo->off();
      float ris = sonar->sonarScan();
      Serial.println(actualPosition);
      Serial.println(ris);
      //send data
      if(ris >= 0.2 && ris <= 0.4){
        state = DETECTED;
      } else {
        state = NOTDETECTED;
      }
      directionOrario ? actualPosition++ : actualPosition--;
      nStateDone += getPeriod();
      break;
    }

    case DETECTED:{
      if(nStateDone == shared->getTimeOfCicle() - (2 * getPeriod())) {
        state = MOVE;
      }
      (nStateDone / getPeriod()) % 2 == 0 ? led_d->switchOff() : led_d->switchOn();
      
      nStateDone += getPeriod();
      break;
    }

    case NOTDETECTED:{
      if(nStateDone == (shared->getTimeOfCicle() - (2 * getPeriod()))) {
        state = MOVE;
      }
      nStateDone += getPeriod();
      break;      
    }

    case MOVE:{
      led_d->switchOff();
      if(actualPosition == -1 || actualPosition == 16){
        servo->off();
        directionOrario = !directionOrario;
        actualPosition += -(actualPosition % (POSITIONS - 1));
        state = STANDBY;
      } else {
        servo->on();
        servo->setPosition(ANGLE * actualPosition + (ANGLE / 2));
        state = SCAN;
      }
      nStateDone = 0;
      break;
    }
  }  
}