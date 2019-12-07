#ifndef __BLINKTASK__
#define __BLINKTASK__

#include "Led.h"
#include "states/Task.h"

class BlinkTask: public Task {
  Light* led;
  enum { ON, OFF} state;

public:

  BlinkTask(Light* led);  
  void init(int period);
  void init();
  void tick();
  void setActive(bool active);
};

#endif

