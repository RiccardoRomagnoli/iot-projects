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
        Serial.println(String(command + " " + mode));
    	return command == "m" && mode == "m";
    }
    return false;
}

bool GUI::checkSingle(){
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String buff = String(msg->getContent());
        String command = buff.substring(0, buff.indexOf(DELIMETER));
        String mode = buff.substring(buff.indexOf(DELIMETER)+1);
    	return command == "m" && mode == "s";
    }
    return false;
}

bool GUI::checkAuto(){
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String buff = String(msg->getContent());
        String command = buff.substring(0, buff.indexOf(DELIMETER));
        String mode = buff.substring(buff.indexOf(DELIMETER)+1);
    	return command == "m" && mode == "a";
    }
    return false;
}

void GUI::sendScan(int angle, float distance){
    MsgService.sendMsg(angle+String(":")+distance);
};

// a:VAL
int GUI::getAngle() {
    Msg* msg = MsgService.receiveMsg();
    if (msg != NULL){
    	String buff = String(msg->getContent());
        String command = buff.substring(0, buff.indexOf(DELIMETER));
        String angleToParse = buff.substring(buff.indexOf(DELIMETER)+1);
    	int angle = angleToParse.toInt();
    	return command == "a" ? angle : -1;
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
    	return command == "s" ? speed : -1;
    } else {
    	return -1;
    }
};
