#ifndef __TASK__
#define __TASK__

class Task {
  int myPeriod;
  int timeElapsed;
  bool active = false;
    
public:
  virtual void init(int period){
    myPeriod = period;  
    timeElapsed = 0;
  }
  
  virtual void init() = 0;

  virtual void stop() = 0;

  virtual void tick() = 0;

  bool updateAndCheckTime(int basePeriod){
    timeElapsed += basePeriod;
    if (timeElapsed >= myPeriod){
      timeElapsed = 0;
      return true;
    } else {
      return false; 
    }
  }
  
  bool isActive(){
    return active;
  }

  virtual void setActive(bool active){
    this->active = active;
    this->active ? init() : stop();
  }

  void setPeriod(int myPeriod){
    this->myPeriod = myPeriod;
  }

  int getPeriod(){
    return myPeriod;
  }
};

#endif
