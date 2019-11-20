#ifndef __PIR__
#define __PIR__
#define TIME_TO_CALIBRATE 10000

class Pir {
public:
  Pir(int pin);
  void calibratePir();
  int readPir();
private:
  int pin;
};
#endif
