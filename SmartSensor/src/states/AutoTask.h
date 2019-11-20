#ifndef __AUTOTASK__
#define __AUTOTASK__

#include "Task.h"

class AutoTask: public Task {

public:
  AutoTask();
  void init(int period); 
  void init();
  void tick();
};

#endif