#ifndef __SINGLETASK__
#define __SINGLETASK__

#include "Task.h"
#include "potenz/Potenziometro.h"
#include "states/SharedState.h"
#include "led/Light.h"
#include "pir/Pir.h"
#include "servo/servo_motor_impl.h"
#include "sonar/Sonar.h"
#include "macros.h"
#include "serial/GUI.h"

class SingleTask: public Task {

  enum { STANDBY, SCAN, DETECTED, NOTDETECTED, MOVE } state;
  
  private:
    Pir* pir;
    Sonar* sonar;
    ServoMotor* servo;
    Light* led_d;
    SharedState* shared;
    GUI* gui;

    int timeElapsed;
    int currentPosition;
    bool directionOrario;
  public:
    SingleTask(Pir* pir, Sonar* sonar, ServoMotor* servo, Light* led_d, SharedState* shared, GUI* gui);
    void init(int period); 
    void init();
    void stop();
    void tick();
  };

#endif