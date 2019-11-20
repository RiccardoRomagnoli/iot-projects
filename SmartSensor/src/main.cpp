#include <Arduino.h>
#include "./scheduler/Scheduler.h"
#include "./states/SingleTask.h"
#include "./states/SetModeTask.h"
#include "./states/ManualTask.h"
#include "./states/AutoTask.h"

Scheduler sched;

void setup() {
  sched.init(125);
  addSetModeTask(addSingleTask(),
                 addManualTask(),
                 addAutoTask());
}

void loop() {
  sched.schedule();
}

void addSetModeTask(Task* SingleTask, Task* ManualTask, Task* AutoTask){
  SetModeTask* t0 = new SetModeTask(SingleTask, ManualTask, AutoTask);
  t0->init(125);
  sched.addTask(t0);
}

Task* addSingleTask(){
  SingleTask* t1 = new SingleTask();
  t1->init(125);
  sched.addTask(t1);
  return t1;
}

Task* addManualTask(){
  ManualTask* t2 = new ManualTask();
  t2->init(125);
  sched.addTask(t2);
  return t2;
}

Task* addAutoTask(){
  AutoTask* t3 = new AutoTask();
  t3->init(125);
  sched.addTask(t3);
  return t3;
}