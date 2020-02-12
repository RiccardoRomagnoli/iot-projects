#ifndef __GUI__
#define __GUI__

#include "MsgServiceBT.h"
#include "Arduino.h"

enum Type { A_TYPE, B_TYPE, C_TYPE, NONE };
enum Command { DEPOSITED, BACK, EXTEND};

const char * const command_str[] =
{
    [DEPOSITED] = "DEPOSITED",
    [BACK] = "BACK",
    [EXTEND]  = "EXTEND"
};

class GUI {

public:
  GUI(int TXDBT_PIN, int RXDBT_PIN);

  Type getType();
  bool checkCommand(Command command);

  void sendConfirm();
  void sendTime(int sec);

  void consumeCmd();

  MsgServiceBT* msgService;
  Msg* lastCommand;
private:
  String sendString;
};

#endif
