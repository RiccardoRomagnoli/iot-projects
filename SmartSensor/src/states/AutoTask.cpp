#include "AutoTask.h"
#include "Arduino.h"

//Gestione allarme
//scan oraria e anti oraria
//raccolta dati, invio dati
//spegnere servo al cambio modalità

AutoTask::AutoTask(Light* led, Sonar* sonar, ServoMotor* servo, SharedState* shared, GUI* gui){
  this->led = led;
  this->sonar = sonar;
  this->servo = servo;
  this->shared = shared;
  this->gui = gui;
}
  
void AutoTask::init(int period){
  Task::init(period);
  this->init();
}

void AutoTask::init(){
  state = SCANNING;
  currentPosition = 0;
  clockwise = true;
  servo->on();
}

void AutoTask::stop(){
  servo->off();
  led->switchOff();
}
  
void AutoTask::tick(){
  switch (state)
  {
    case SCANNING:{
      this->distance = sonar->sonarScan();
      if(distance >= D_MIN && distance <= D_MAX){
        //Object detected
        alarm = true;
        alarmClockwise = clockwise;
        state = ALARM;
        //led on
      }else if (distance > D_MAX){
        //Object not detected    
        led->switchOff();
        distance = 0;
        if(alarm){
          state = ALARM;
        }else{
          state = IDLE;
        }
      }else if (distance < D_MIN){
        //Alarm With Trackings
        state = TRACKING;
      }
      gui->sendScan(ANGLE * currentPosition + OFFSET, distance);
      timeElapsed = this->getPeriod();
      break;
    }
    case ALARM:{
      //If remains last two ticks
      if(timeElapsed == shared->getTimeOfCicle() - (2 * getPeriod())) {
        state = MOVE;
      }
      timeElapsed % 2 == 0 ? led->switchOff() : led->switchOn();
      timeElapsed += this->getPeriod();
      break;

    case IDLE:
      //If remains last two ticks
      if(timeElapsed == shared->getTimeOfCicle() - (2 * getPeriod())) {
        state = MOVE;
      }
      timeElapsed += this->getPeriod();
      break;
    }
    case MOVE:{
      led->switchOff();
      servo->setPosition(ANGLE * currentPosition + OFFSET);
      if(clockwise)
        ++currentPosition;
      else
        --currentPosition;
      if(currentPosition == -1 || currentPosition == POSITIONS){
        clockwise = !clockwise;
        currentPosition += -(currentPosition % (POSITIONS - 1));
        if(alarm && clockwise == alarmClockwise )
          alarm = false;
        shared->updateTimeOfCicle();
      }
      state = SCANNING;
      break;
    }
    case TRACKING:{
      led->switchOn();
      state = SCANNING;
      timeElapsed += this->getPeriod();
      break;
    }
  }
}