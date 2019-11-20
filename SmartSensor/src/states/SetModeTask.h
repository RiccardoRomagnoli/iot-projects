#ifndef __SETMODETASK__
#define __SETMODETASK__

#include "Task.h"

class SetModeTask: public Task {

public:
  SetModeTask();
  void init(int period);  
  void tick();
};

#endif