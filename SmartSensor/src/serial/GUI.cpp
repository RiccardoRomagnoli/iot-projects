#include "GUI.h"
#include "Arduino.h"
#include "MsgService.h"

GUI::GUI(){
}

bool GUI::checkManual(){
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String mode = String(msg->getContent());
    	return mode == "m";
    }
    return false;
}

bool GUI::checkSingle(){
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String mode = String(msg->getContent());
    	return mode == "m";
    }
    return false;
}

bool GUI::checkAuto(){
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String mode = String(msg->getContent());
    	return mode == "m";
    }
    return false;
}

void GUI::sendScan(int angle, float distance){
    MsgService.sendMsg(angle+String(":")+distance);
};

// ANGLE</sp>VAL
int GUI::getAngle() {
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	int angle = String(msg->getContent()).toInt();
    	return angle;
    } else {
    	return -1;
    }
};
// SPEED</sp>VAL
int GUI::getSpeed() {
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	int angle = String(msg->getContent()).toInt();
    	return angle;
    } else {
    	return -1;
    }
};
