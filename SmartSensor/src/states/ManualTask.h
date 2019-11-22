#ifndef __MANUALTASK__
#define __MANUALTASK__

#include "Task.h"
#include "servo/servo_motor_impl.h"
#include "sonar/Sonar.h"
#include "serial/GUI.h"
#include "macros.h"

class ManualTask: public Task {

enum { RECEIVING, MOVE, SCAN } state;

public:
  ManualTask(Sonar* sonar, ServoMotor* servo, GUI* gui);
  void init(int period); 
  void init();
  void stop();
  void tick();
private:
  Sonar* sonar;
  ServoMotor* servo;
  GUI* gui;
  int angle;

};

#endif