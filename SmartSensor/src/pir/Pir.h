#ifndef __PIR__
#define __PIR__

class Pir {
public:
  Pir(int pin);
  bool checkPirMovement();
private:
  int pin;
};
#endif
