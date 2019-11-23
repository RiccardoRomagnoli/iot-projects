#ifndef __GUI__
#define __GUI__

#include "Arduino.h"

class GUI {

public:
  GUI();

  bool checkManual();
  bool checkAuto();
  bool checkSingle();

  int getAngle();
  int getSpeed();

  void sendScan(int angle, float distance);
  void sendCurrentMode(String mode);

};

#endif
