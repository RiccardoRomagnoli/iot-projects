#include "SingleTask.h"
#include "Arduino.h"
#include "avr/sleep.h"

void wakeUp(){}

SingleTask::SingleTask(Pir* pir, Sonar* sonar, ServoMotor* servo, Light* led_d, SharedState* shared, GUI* gui){
  this->shared = shared;
  this->pir = pir;
  this->sonar = sonar;
  this->servo = servo;
  this->led_d = led_d;
  this->gui = gui;
}

void SingleTask::init(int period){
  Task::init(period);
  attachInterrupt(digitalPinToInterrupt(PIR), wakeUp, RISING);
  this->init();
}

void SingleTask::init(){
  currentPosition = 0;
  state = STANDBY;
  directionOrario = true;
}

void SingleTask::stop(){
  servo->off();
  led_d->switchOff();
}

void SingleTask::tick(){

  switch (state) {
    case STANDBY:{
      set_sleep_mode(SLEEP_MODE_PWR_DOWN);  
      sleep_enable();
      sleep_mode();  

      bool det = true; // pir attivato
      if (det) {
        state = SCAN;
        servo->on();        
      }
      break;
    }

    case SCAN:{
      float ris = sonar->sonarScan();
      if(ris <= D_MAX){
        gui->sendScan(currentPosition * ANGLE + (ANGLE / 2), ris);
        state = DETECTED;
      } else {
        gui->sendScan(currentPosition * ANGLE + (ANGLE / 2), 0);
        state = NOTDETECTED;
      }
      directionOrario ? currentPosition++ : currentPosition--;
      timeElapsed = getPeriod();
      break;
    }

    case DETECTED:{
      if(timeElapsed == shared->getTimeOfCicle() - (2 * getPeriod())) {
        state = MOVE;
      }
      (timeElapsed / getPeriod()) % 2 == 0 ? led_d->switchOff() : led_d->switchOn();
      
      timeElapsed += getPeriod();
      break;
    }

    case NOTDETECTED:{
      if(timeElapsed == (shared->getTimeOfCicle() - (2 * getPeriod()))) {
        state = MOVE;
      }
      timeElapsed += getPeriod();
      break;      
    }

    case MOVE:{
      led_d->switchOff();
      if(currentPosition == -1 || currentPosition == POSITIONS){
        servo->off();
        directionOrario = !directionOrario;
        currentPosition += -(currentPosition % (POSITIONS - 1));
        state = STANDBY;
        shared->updateTimeOfCicle();
      } else {
        servo->setPosition(ANGLE * currentPosition + (ANGLE / 2));
        state = SCAN;
      }
      break;
    }
  }  
}