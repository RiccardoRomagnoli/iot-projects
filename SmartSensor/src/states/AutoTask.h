#ifndef __AUTOTASK__
#define __AUTOTASK__

#include "Task.h"
#include "./pir/Pir.h"
#include "./servo/servo_motor_impl.h"
#include "./sonar/Sonar.h"
#include "./macros.h"

class AutoTask: public Task {

public:
  AutoTask(Task* blinkTask);
  void init(int period); 
  void init();
  void tick();
private:
  Sonar* sonar;
  ServoMotor* servo;
  Task* blinkTask;
  int actualPosition;
  bool clockwise;
  bool alarm;
  bool alarmClockwise;
  bool alarmTracking;
  float results[POSITIONS];

  void setAlarm(bool status);
  void checkObject();
  void checkEndScan();
};

#endif