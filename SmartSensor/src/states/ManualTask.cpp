#include "ManualTask.h"
#include "Arduino.h"

ManualTask::ManualTask(){
  
}
  
void ManualTask::init(int period){
  Task::init(period); 
}
  
void ManualTask::tick(){

}