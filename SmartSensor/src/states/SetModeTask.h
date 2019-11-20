#ifndef __SETMODETASK__
#define __SETMODETASK__

#include "Task.h"

class SetModeTask: public Task {

Task* SingleTask;
Task* ManualTask;
Task* AutoTask;

public:
  SetModeTask(Task* SingleTask, Task* ManualTask, Task* AutoTask);
  void init(int period);  
  void tick();
};

#endif