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

bool GUI::checkDeposit(){
  if (msgService->isMsgAvailable()) {
    Msg* msg = msgService->receiveMsg();
    Serial.println(msg->getContent());    
    if (msg->getContent() == "DEPOSITED"){
       return true;
    }else{
       return false;
    }
    delete msg;
  }else{
    return false;
  }
}

bool GUI::checkExtend(){
  if (msgService->isMsgAvailable()) {
    Msg* msg = msgService->receiveMsg();
    Serial.println(msg->getContent());    
    if (msg->getContent() == "EXTEND"){
       return true;
    }else{
       return false;
    }
    delete msg;
  }else{
    return false;
  }
}

bool GUI::checkBack(){
  if (msgService->isMsgAvailable()) {
    Msg* msg = msgService->receiveMsg();
    Serial.println(msg->getContent());    
    if (msg->getContent() == "BACK"){
       return true;
    }else{
       return false;
    }
    delete msg;
  }else{
    return false;
  }
}

void GUI::sendConfirm(){
    msgService->sendMsg(Msg("DEPOSITED"));
};

void GUI::sendTime(int sec){
    msgService->sendMsg(Msg(String(sec)));
}