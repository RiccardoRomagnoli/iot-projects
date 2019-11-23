#ifndef __SETMODETASK__
#define __SETMODETASK__

#include "Task.h"
#include "button/Button.h"
#include "serial/GUI.h"
#include "potenz/Potenziometro.h"
#include "SharedState.h"
#include "config.h"

class SetModeTask: public Task {
private:
  Task* singleTask;
  Task* manualTask;
  Task* autoTask;
  SharedState* sharedState;

  Button* singleButton;
  Button* manualButton;
  Button* autoButton;

  GUI* gui;
  Potenziometro* pot;
  enum { MANUAL, SINGLE, AUTO } state;
public:
  SetModeTask(Task* singleTask, Task* manualTask, Task* autoTask,
   Button* singleButton, Button* manualButton, Button* autoButton,
   Potenziometro* pot, GUI* gui, SharedState* sharedState);
  void init(int period);  
  void init();
  void stop();
  void tick();
};

#endif