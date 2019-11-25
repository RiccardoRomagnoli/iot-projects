#include "SingleTask.h"
#include "Arduino.h"
#include "avr/sleep.h"
#include "../lib/EnableInterrupt/EnableInterrupt.h"

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
  enableInterrupt(PIR, wakeUp, RISING);
  enableInterrupt(BUTTON_AUTO, wakeUp, RISING);
  enableInterrupt(BUTTON_MANUAL, wakeUp, RISING);
  enableInterrupt(BUTTON_SINGLE, wakeUp, RISING);
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
      delay(5);
      set_sleep_mode(SLEEP_MODE_PWR_DOWN);  
      sleep_enable();
      sleep_mode();  

      state = SCAN;
      servo->on();        
      break;
    }

    case SCAN:{
      float ris = sonar->sonarScan();
      if(ris <= D_MAX){
        gui->sendScan(ANGLE * currentPosition + OFFSET, ris);
        state = DETECTED;
      } else {
        gui->sendScan(ANGLE * currentPosition + OFFSET, 0);
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
        servo->setPosition(ANGLE * currentPosition + OFFSET);
        state = SCAN;
      }
      break;
    }
  }  
}