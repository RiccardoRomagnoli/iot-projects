#include <Arduino.h>
#include "scheduler/Scheduler.h"
#include "states/SharedState.h"
#include "states/SingleTask.h"
#include "states/SetModeTask.h"
#include "states/ManualTask.h"
#include "states/AutoTask.h"
#include "led/BlinkTask.h"
#include "potenz/Potenziometro.h"
#include "led/Led.h"
#include "pir/Pir.h"
#include "servo/servo_motor_impl.h"
#include "sonar/Sonar.h"
#include "macros.h"
#include "main.h"
#include "macros.h"
#include "serial/MsgService.h"
#include "serial/GUI.h"

Scheduler sched;
Task *t0, *t1, *t2, *t3;
void setup() {
  MsgService.init();
  sched.init(25);

  SharedState* shared = new SharedState(DEFAULT_EXECUTION_TIME/POSITIONS);
  Pir* pir = new Pir(PIR);
  Sonar* sonar = new Sonar(TRIGSONAR, ECHOSONAR);
  ServoMotor* servo = new ServoMotorImpl(SERVOMOTOR);
  Light* ledD = new Led(LED_D);
  Light* ledA = new Led(LED_A);
  Button* singleButton = new Button(BUTTON_SINGLE);
  Button* manualButton = new Button(BUTTON_MANUAL);
  Button* autoButton = new Button(BUTTON_AUTO);
  GUI* gui = new GUI();
  Potenziometro* pot = new Potenziometro(POT);

  t1 = new SingleTask(pir, sonar, servo, ledD, shared, gui);
  t1->init(25);
  t1->setActive(false);

  t2 = new ManualTask(sonar, servo, gui);
  t2->init(25);
  t2->setActive(true);


  t3 = new AutoTask(ledA, sonar, servo, shared, gui);
  t3->init(25);
  t3->setActive(false);


  t0 = new SetModeTask(t1, t2, t3, singleButton, manualButton, autoButton, pot, gui, shared);
  t0->init(125);
  t0->setActive(true);

  sched.addTask(t0);
  sched.addTask(t1);
  sched.addTask(t2);
  sched.addTask(t3);

}

void loop() {
  sched.schedule();
}