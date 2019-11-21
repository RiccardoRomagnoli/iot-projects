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
Led* led_d;
Task* blinkTask;
float results[POSITIONS];
bool attivo;
int actualPosition;
bool directionOrario;

public:
  SingleTask(Task* blinkTask, Pir* pir, Sonar* sonar, ServoMotor* servo);
  void init(int period); 
  void init(); 
  void tick();
};

#endif