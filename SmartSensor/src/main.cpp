#include <Arduino.h>
#include "./scheduler/Scheduler.h"
#include "./states/SingleTask.h"
#include "./states/SetModeTask.h"
#include "./states/ManualTask.h"
#include "./states/AutoTask.h"
#include "./led/BlinkTask.h"
#include "main.h"
#include "macros.h"

Scheduler sched;
BlinkTask* blinkTask;

void setup() {
  Serial.begin(9600);
  sched.init(25);
  blinkTask = new BlinkTask(LED_D);
  blinkTask->init(50);
  blinkTask->setActive(false);

  addSetModeTask(addSingleTask(),
                 addManualTask(),
                 addAutoTask());
  sched.addTask(blinkTask);
}

void loop() {
  sched.schedule();
}

void addSetModeTask(Task* singleTask, Task* manualTask, Task* autoTask){
  SetModeTask* t0 = new SetModeTask(singleTask, manualTask, autoTask);
  t0->init(125);
  t0->setActive(true);
  sched.addTask(t0);
}

Task* addSingleTask(){
  SingleTask* t1 = new SingleTask(blinkTask);
  t1->init(125);
  t1->setActive(false);
  sched.addTask(t1);
  return t1;
}

Task* addManualTask(){
  ManualTask* t2 = new ManualTask();
  t2->init(125);
  t2->setActive(true);
  sched.addTask(t2);
  return t2;
}

Task* addAutoTask(){
  AutoTask* t3 = new AutoTask();
  t3->init(125);
  t3->setActive(false);
  sched.addTask(t3);
  return t3;
}