#ifndef __BUTTON__
#define __BUTTON__

class Button {
public:
  Button(int pin);
  int readButton();
private:
  int pin;
};
#endif
