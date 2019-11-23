#ifndef __SHARED_STATE__
#define __SHARED_STATE__

#include "Arduino.h"

class SharedState {

private:
  int timeOfCicle;
  int tempTimeOfCicle;
  int tempTimeOfCicleByPot;
public:
  
  SharedState(int timeOfCicle){
    this->timeOfCicle = timeOfCicle;
  }
  
  
  int getTimeOfCicle(){ return this->timeOfCicle; }
  
  //time of a single scan
  void setTempTimeOfCicle(int tempTimeOfCicle){ this->tempTimeOfCicle = tempTimeOfCicle; }

  //read only the change of potenziometer
  void setTempTimeOfCicleByPot(int tempTimeOfCicle){
    if(tempTimeOfCicleByPot != tempTimeOfCicle){
      setTempTimeOfCicle(tempTimeOfCicle);
      tempTimeOfCicleByPot = tempTimeOfCicle;
    }
  }

  void updateTimeOfCicle(){this->timeOfCicle = this->tempTimeOfCicle; }

};

#endif
