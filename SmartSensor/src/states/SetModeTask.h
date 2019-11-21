#ifndef __SETMODETASK__
#define __SETMODETASK__

#include "Task.h"
#include "./button/Button.h"
#include "./macros.h"

class SetModeTask: public Task {

Task* singleTask;
Task* manualTask;
Task* autoTask;
Task* currentModeTask;

Button* singleButton;
Button* manualButton;
Button* autoButton;

public:
  SetModeTask(Task* singleTask, Task* manualTask, Task* autoTask, Button* singleButtin, Button* manualButton, Button* autoButton);
  void init(int period);  
  void init();
  void tick();
private:
  void checkButtonPressed();
  void checkSerialReceived();
};

#endif