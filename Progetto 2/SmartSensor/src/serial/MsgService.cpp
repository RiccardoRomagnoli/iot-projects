#include "Arduino.h"
#include "MsgService.h"

String content;

MsgServiceClass MsgService;

bool MsgServiceClass::isMsgAvailable(){
  return msgAvailable;
}

Msg* MsgServiceClass::receiveMsg(){
  if (msgAvailable){
    Msg* msg = currentMsg;
    return msg;  
  } else {
    return NULL; 
  }
}
bool MsgServiceClass::clear(){
  currentMsg = NULL;
  msgAvailable = false;
  content = "";
  return true;
}

void MsgServiceClass::init(){
  Serial.begin(9600);
  content.reserve(256);
  content = "";
  currentMsg = NULL;
  msgAvailable = false;  
}

void MsgServiceClass::sendMsg(const String& msg){
  Serial.println(msg);
  //Clear data send from buffer
  MsgService.clear();
}

void serialEvent() {
  /* reading the content */
  while (Serial.available()) {
    char ch = (char) Serial.read();
    if (ch == '\n'){
      MsgService.currentMsg = new Msg(content);
      MsgService.msgAvailable = true;
    } else {
      content += ch;      
    }
  }
}  


