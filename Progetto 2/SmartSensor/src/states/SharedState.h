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
  void setTempTimeOfCicle(int _tempTimeOfCicle){ this->tempTimeOfCicle = _tempTimeOfCicle; }

  //read only the change of potenziometer
  void setTempTimeOfCicleByPot(int _tempTimeOfCicle){
    if(tempTimeOfCicleByPot != _tempTimeOfCicle){
      setTempTimeOfCicle(_tempTimeOfCicle);
      tempTimeOfCicleByPot = _tempTimeOfCicle;
    }
  }

  void updateTimeOfCicle(){this->timeOfCicle = this->tempTimeOfCicle; }

};

#endif
