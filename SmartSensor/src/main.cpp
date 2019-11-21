#include <Arduino.h>
#include "./scheduler/Scheduler.h"
#include "./states/SingleTask.h"
#include "./states/SetModeTask.h"
#include "./states/ManualTask.h"
#include "./states/AutoTask.h"
#include "./led/BlinkTask.h"
#include "./potenz/Potenziometro.h"
#include "./led/Led.h"
#include "./pir/Pir.h"
#include "./servo/servo_motor_impl.h"
#include "./sonar/Sonar.h"
#include "./macros.h"
#include "main.h"
#include "macros.h"

Scheduler sched;

void setup() {
  Serial.begin(9600);
  sched.init(25);

  Pir* pir = new Pir(PIR);
  Sonar* sonar = new Sonar(TRIGSONAR, ECHOSONAR);
  ServoMotor* servo = new ServoMotorImpl(SERVOMOTOR);
  Light* ledD = new Led(LED_D);
  Light* ledA = new Led(LED_A);
  Button* singleButton = new Button(BUTTON_SINGLE);
  Button* manualButton = new Button(BUTTON_MANUAL);
  Button* autoButton = new Button(BUTTON_AUTO);

  Task* t0 = new BlinkTask(ledD);
  t0->init(50);
  t0->setActive(false);
  sched.addTask(t0);

  Task* t1 = new SingleTask(t0, pir, sonar, servo, ledD);
  t1->init(125);
  t1->setActive(false);
  sched.addTask(t1);

  Task* t2 = new ManualTask();
  t2->init(125);
  t2->setActive(true);
  sched.addTask(t2);

  Task* t3 = new AutoTask(t0, sonar, servo);
  t3->init(125);
  t3->setActive(false);
  sched.addTask(t3);

  Task* t4 = new SetModeTask(t1, t2, t3, singleButton, manualButton, autoButton);
  t4->init(125);
  t4->setActive(true);
  sched.addTask(t4);

}

void loop() {
  sched.schedule();
}