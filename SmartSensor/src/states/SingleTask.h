#ifndef __SINGLETASK__
#define __SINGLETASK__

#include "Task.h"

class SingleTask: public Task {

public:
  SingleTask();
  void init(int period);  
  void tick();
};

#endif