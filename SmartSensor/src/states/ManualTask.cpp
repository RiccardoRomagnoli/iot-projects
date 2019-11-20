#include "ManualTask.h"
#include "Arduino.h"

ManualTask::ManualTask(){
  
}
  
void ManualTask::init(int period){
  Task::init(period);
  this->init();
}

void ManualTask::init(){

}
  
void ManualTask::tick(){
  Serial.println("Modalita manual attiva");
}