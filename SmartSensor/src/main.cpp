#include <Arduino.h>
#include "./scheduler/Scheduler.h"

Scheduler sched;

void setup() {
  sched.init(125);

}

void loop() {
  sched.schedule();

}