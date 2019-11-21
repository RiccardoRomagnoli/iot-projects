#ifndef __SHARED_STATE__
#define __SHARED_STATE__

class SharedState {

private:
  int timeOfCicle;

public:
  
  SharedState(int timeOfCicle){
    this->timeOfCicle = timeOfCicle;
  }
  
  
  int getTimeOfCicle(){ return this->timeOfCicle; }
  void setTimeOfCicle(int timeOfCicle){ this->timeOfCicle = timeOfCicle; }

};

#endif
