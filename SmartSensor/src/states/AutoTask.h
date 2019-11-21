#ifndef __AUTOTASK__
#define __AUTOTASK__

#include "Task.h"
#include "./states/SharedState.h"
#include "./pir/Pir.h"
#include "./servo/servo_motor_impl.h"
#include "./sonar/Sonar.h"
#include "./macros.h"

class AutoTask: public Task {

enum { SCANNING, MOVE, ALARM, TRACKING, IDLE, SEND } state;

public:
  AutoTask(Task* blinkTask, Sonar* sonar, ServoMotor* servo, SharedState* shared);
  void init(int period); 
  void init();
  void tick();
  
  
private:
  Sonar* sonar;
  ServoMotor* servo;
  Task* blinkTask;
  SharedState* shared;
  int actualPosition;
  int timeElapsed;
  float distance;
  bool clockwise;
  bool alarm;
  bool alarmClockwise;
  bool alarmTracking;
};

#endif