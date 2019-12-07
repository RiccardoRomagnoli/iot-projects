#ifndef __POTENZ__
#define __POTENZ__

class Potenziometro {
public:
  Potenziometro(int pin);
  int readPotenziometro();
private:
  int pin;
};
#endif