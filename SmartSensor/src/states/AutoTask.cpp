#include "AutoTask.h"
#include "Arduino.h"

AutoTask::AutoTask(){
  
}
  
void AutoTask::init(int period){
  Task::init(period); 
}
  
void AutoTask::tick(){

}