#include "SingleTask.h"
#include "Arduino.h"

SingleTask::SingleTask(){
  
}
  
void SingleTask::init(int period){
  Task::init(period); 
}
  
void SingleTask::tick(){

}