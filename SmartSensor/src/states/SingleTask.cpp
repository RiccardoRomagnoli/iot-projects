#include "SingleTask.h"
#include "Arduino.h"


SingleTask::SingleTask(){
  pir = new Pir(PIR);
  sonar = new Sonar(TRIGSONAR, ECHOSONAR);
  servo = new ServoMotorImpl(SERVOMOTOR);
}

void SingleTask::init(int period){
  Task::init(period);
  attivo = false;
  for(int i = 0; i < POSITIONS; i++){
    results[i] = 0;
  }
  actualPosition = 0;
  directionOrario = true;
}

void SingleTask::tick(){
  if(attivo == true || pir->readPir() == HIGH) {
   attivo = true;
   results[actualPosition] = sonar->sonarScan(); 
   Serial.println(results[actualPosition]);
   directionOrario == true ? actualPosition++ : actualPosition++;
   if(actualPosition == 15 || actualPosition == 0){
     attivo = false;
     directionOrario = !directionOrario;
   } else {
    servo->on();
    servo->setPosition(180 / POSITIONS * actualPosition); 
   }

  } else {

  }

}