#ifndef __SERIAL__
#define __SERIAL__

#include <string.h>

class SerialCommunication{
    public:
        SerialCommunication();
        char* getMessage();
        void sendMessage(char* msgToSend);
};


#endif