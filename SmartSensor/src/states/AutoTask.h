#ifndef __AUTOTASK__
#define __AUTOTASK__

#include "Task.h"
#include "states/SharedState.h"
#include "servo/servo_motor_impl.h"
#include "sonar/Sonar.h"
#include "macros.h"
#include "led/Light.h"
#include "serial/GUI.h"

class AutoTask: public Task {

enum { SCANNING, MOVE, ALARM, TRACKING, IDLE } state;

public:
  AutoTask(Light* led, Sonar* sonar, ServoMotor* servo, SharedState* shared, GUI* gui);
  void init(int period); 
  void init();
  void stop();
  void tick();
  
private:
  Sonar* sonar;
  ServoMotor* servo;
  SharedState* shared;
  Light* led;
  GUI* gui;
  int currentPosition;
  int timeElapsed;
  float distance;
  bool clockwise;
  bool alarm;
  bool alarmClockwise;
  bool alarmTracking;
};

#endif