#ifndef __SONAR__
#define __SONAR__

class Sonar {
  const float vs = 331.45 + 0.62*20;
public:
  Sonar(int trigPin, int echoPin);
  float sonarScan();
private:
  int trigPin;
  int echoPin;
};
#endif