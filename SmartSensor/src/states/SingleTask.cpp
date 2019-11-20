#include "SingleTask.h"
#include "Arduino.h"


SingleTask::SingleTask(){
  pir = new Pir(PIR);
  sonar = new Sonar(TRIGSONAR, ECHOSONAR);
  servo = new ServoMotorImpl(SERVOMOTOR);
  led_d = new Led(LED_D);
}

void SingleTask::init(int period){
  Task::init(period);
  this->init();
}

void SingleTask::init(){
  attivo = false;
  for(int i = 0; i < POSITIONS; i++){
    results[i] = 0;
  }
  actualPosition = ANGLE / 2;
  directionOrario = true;
}

void SingleTask::tick(){
  if(attivo || pir->checkPirMovement()) {
   attivo = true;
   results[actualPosition] = sonar->sonarScan(); 
   if(results[actualPosition] >= 0.2 && results[actualPosition] <= 0.4){
     led_d->switchOn();
     delay(20);
     led_d->switchOff();
   }
   directionOrario ? actualPosition++ : actualPosition--;
   if(actualPosition == 16 || actualPosition == -1){
     attivo = false;
     directionOrario = !directionOrario;
     actualPosition += -(actualPosition % (POSITIONS - 1));
     servo->off();
     //invia dati
   } else {
    servo->on();
    servo->setPosition(ANGLE * actualPosition + (ANGLE / 2)); 
   }
  } else {
    //risparmio energetico
  }
  
}