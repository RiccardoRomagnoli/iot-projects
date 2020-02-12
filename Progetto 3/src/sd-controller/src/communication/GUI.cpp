#include "GUI.h"
#include "Arduino.h"
#include "MsgServiceBT.h"
#include "string.h"

using namespace std;

GUI::GUI(int TXDBT_PIN, int RXDBT_PIN){
    msgService = new MsgServiceBT(TXDBT_PIN, RXDBT_PIN);
    msgService->init();
}

Type GUI::getType(){
  if (msgService->isMsgAvailable()) {
    Msg* msg = msgService->receiveMsg();
    Serial.println(msg->getContent());    
    if (msg->getContent() == "A"){
        return A_TYPE;
    } else if (msg->getContent() == "B"){
        return B_TYPE;
    } else if (msg->getContent() == "C"){
        return C_TYPE;
    }else{
        return NONE;
    }
    delete msg;
  }else{
      return NONE;
  }
}

bool GUI::checkCommand(Command command){
  if (msgService->isMsgAvailable()) {
    lastCommand = msgService->receiveMsg();
    Serial.println(lastCommand->getContent());    
    return lastCommand->getContent() == command_str[command];
  }
  if(lastCommand != NULL){
    return lastCommand->getContent() == command_str[command];
  }
  return false;
}

void GUI::sendConfirm(){
    msgService->sendMsg(Msg("DEPOSITED"));
};

void GUI::sendTime(int sec){
    sendString = String("TIME:")+sec;
    msgService->sendMsg(sendString);
}

void GUI::consumeCmd(){
  lastCommand = NULL;
  delete lastCommand;
}