#ifndef __MAINTASK__
#define __MAINTASK__

#include "Task.h"
#include "servo/servo_motor_impl.h"
#include "led/Light.h"
#include "communication/GUI.h"

class MainTask: public Task {

enum { ACCEPTING, TIMING, TIMEOUT, IDLE } state;

public:
  MainTask(ServoMotor* servo, Light* ledA, Light* ledB, Light* ledC, GUI* gui);
  void init(int period); 
  void init();
  void stop();
  void tick();
  
private:
  ServoMotor* servo;
  Light* ledA;
  Light* ledB;
  Light* ledC;
  GUI* gui;
  int timeElapsed;
};

#endif