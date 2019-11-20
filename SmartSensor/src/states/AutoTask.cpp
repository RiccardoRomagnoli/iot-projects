#include "AutoTask.h"
#include "Arduino.h"

AutoTask::AutoTask(){
  
}
  
void AutoTask::init(int period){
  Task::init(period);
  this->init();
}

void AutoTask::init(){

}
  
void AutoTask::tick(){
  Serial.println("Modalita auto attiva");
}