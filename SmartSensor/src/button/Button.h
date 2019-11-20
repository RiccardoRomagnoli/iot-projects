#ifndef __BUTTON__
#define __BUTTON__

class Button {
public:
  Button(int pin);
  int readButton();
  bool checkPressed();
private:
  int pin;
};
#endif
