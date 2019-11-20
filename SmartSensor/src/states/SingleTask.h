#ifndef __SINGLETASK__
#define __SINGLETASK__

#include "Task.h"
#include "./potenz/Potenziometro.h"
#include "./led/Led.h"
#include "./pir/Pir.h"
#include "./servo/servo_motor_impl.h"
#include "./sonar/Sonar.h"
#include "./macros.h"

class SingleTask: public Task {

Pir* pir;
Sonar* sonar;
ServoMotor* servo;
float results[POSITIONS];
bool attivo;
int actualPosition;
bool directionOrario;

public:
  SingleTask();
  void init(int period);  
  void tick();
};

#endif