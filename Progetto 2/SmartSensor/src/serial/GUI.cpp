#include "GUI.h"
#include "Arduino.h"
#include "MsgService.h"
#include "string.h"

#define DELIMETER ':'

GUI::GUI(){
}

bool GUI::checkManual(){
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String buff = String(msg->getContent());
        String command = buff.substring(0, buff.indexOf(DELIMETER));
        String mode = buff.substring(buff.indexOf(DELIMETER)+1);
    	if(command == "m" && mode == "m")
            return MsgService.clear();
        else
            return false;
    }
    return false;
}

bool GUI::checkSingle(){
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String buff = String(msg->getContent());
        String command = buff.substring(0, buff.indexOf(DELIMETER));
        String mode = buff.substring(buff.indexOf(DELIMETER)+1);
    	if(command == "m" && mode == "s")
            return MsgService.clear();
        else
            return false;
    }
    return false;
}

bool GUI::checkAuto(){
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String buff = String(msg->getContent());
        String command = buff.substring(0, buff.indexOf(DELIMETER));
        String mode = buff.substring(buff.indexOf(DELIMETER)+1);
    	if(command == "m" && mode == "a")
            return MsgService.clear();
        else
            return false;
    }
    return false;
}

void GUI::sendScan(int angle, float distance){
    MsgService.sendMsg(angle+String(":")+distance);
};

void GUI::sendCurrentMode(String mode){
    MsgService.sendMsg("arduino:m:" + mode);
}

// a:VAL
int GUI::getAngle() {
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String buff = String(msg->getContent());
        String command = buff.substring(0, buff.indexOf(DELIMETER));
        String angleToParse = buff.substring(buff.indexOf(DELIMETER)+1);
    	int angle = angleToParse.toInt();
        if(command == "a") {
            MsgService.clear();
            return angle;
        } else {
            return -1;
        }
    } else {
    	return -1;
    }
};
// s:VAL
int GUI::getSpeed() {
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String buff = String(msg->getContent());
        String command = buff.substring(0, buff.indexOf(DELIMETER));
        String speedToParse = buff.substring(buff.indexOf(DELIMETER)+1);
    	int speed = speedToParse.toInt();
        if(command == "s") {
            MsgService.clear();
            return speed;
        } else {
            return -1;
        }
    } else {
    	return -1;
    }
};
