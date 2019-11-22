#ifndef __SHARED_STATE__
#define __SHARED_STATE__

class SharedState {

private:
  int timeOfCicle;
  int tempTimeOfCicle;
public:
  
  SharedState(int timeOfCicle){
    this->timeOfCicle = timeOfCicle;
  }
  
  
  int getTimeOfCicle(){ return this->timeOfCicle; }
  
  //time of a single scan
  void setTempTimeOfCicle(int tempTimeOfCicle){ this->tempTimeOfCicle = tempTimeOfCicle; }

  void updateTimeOfCicle(){this->timeOfCicle = this->tempTimeOfCicle; }

};

#endif
