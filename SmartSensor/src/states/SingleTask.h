#ifndef __SINGLETASK__
#define __SINGLETASK__

#include "Task.h"
#include "./potenz/Potenziometro.h"
#include "./states/SharedState.h"
#include "./led/Light.h"
#include "./pir/Pir.h"
#include "./servo/servo_motor_impl.h"
#include "./sonar/Sonar.h"
#include "./macros.h"

class SingleTask: public Task {

  enum { STANDBY, SCAN, DETECTED, NOTDETECTED, MOVE } state;
  
  private:
    Pir* pir;
    Sonar* sonar;
    ServoMotor* servo;
    Light* led_d;
    Task* blinkTask;
    SharedState* shared;

    int nStateDone;
    int actualPosition;
    bool directionOrario;
  public:
    SingleTask(Task* blinkTask, Pir* pir, Sonar* sonar, ServoMotor* servo, Light* led_d, SharedState* shared);
    void init(int period); 
    void init(); 
    void tick();
  };

#endif