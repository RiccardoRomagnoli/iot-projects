#include <Arduino.h>
#include "./scheduler/Scheduler.h"
#include "./states/SingleTask.h"

Scheduler sched;

void setup() {
  sched.init(125);
  SingleTask* t1 = new SingleTask();
  t1->init(125);
  sched.addTask(t1);
}

void loop() {
  sched.schedule();

}