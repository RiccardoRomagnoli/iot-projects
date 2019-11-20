#include "Arduino.h"
#include "SetModeTask.h"
#include "Task.h"

SetModeTask::SetModeTask(Task* SingleTask, Task* ManualTask, Task* AutoTask){
  this->SingleTask = SingleTask;
  this->ManualTask = ManualTask;
  this->AutoTask = AutoTask;
}
  
void SetModeTask::init(int period){
  Task::init(period); 
}
  
void SetModeTask::tick(){
  
}