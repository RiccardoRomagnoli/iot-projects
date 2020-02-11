#include "SoftwareSerial.h"
#include "Arduino.h"
#include "servo/servo_motor_impl.h"
#include "led/Led.h"
#include "tasks/MainTask.h"
#include "scheduler/Scheduler.h"
#include "communication/GUI.h"

/*
 *  BT module connection:  
 *  - pin 2 <=> TXD
 *  - pin 3 <=> RXD
 *
 */
#define SERVO_PIN 13
#define LEDA_PIN 10
#define LEDB_PIN 9
#define LEDC_PIN 8
#define TXDBT_PIN 2
#define RXDBT_PIN 3

Scheduler sched;
Task *t;



void setup() {
  sched.init(25);

  ServoMotor* servo = new ServoMotorImpl(SERVO_PIN);
  Light* ledA = new Led(LEDA_PIN);
  Light* ledB = new Led(LEDB_PIN);
  Light* ledC = new Led(LEDC_PIN);
  GUI* gui = new GUI(TXDBT_PIN, RXDBT_PIN);

  t = new MainTask(servo, ledA, ledB, ledC, gui);
  t->init(25);
  t->setActive(true);
  sched.addTask(t);

  Serial.begin(9600);
  while (!Serial){}
  Serial.println("Communication is Ready.");
}

void loop() {
  sched.schedule();
}
