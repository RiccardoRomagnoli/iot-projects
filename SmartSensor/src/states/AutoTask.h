#ifndef __AUTOTASK__
#define __AUTOTASK__

#include "Task.h"
#include "./pir/Pir.h"
#include "./servo/servo_motor_impl.h"
#include "./sonar/Sonar.h"
#include "./macros.h"

class AutoTask: public Task {

public:
  AutoTask(Task* blinkTask, Sonar* sonar, ServoMotor* servo);
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

  void setAlarm(bool status);
  float checkObject();
  void checkEndScan();
};

#endif