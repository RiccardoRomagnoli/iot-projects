#ifndef __GUI__
#define __GUI__

#include "MsgServiceBT.h"
#include "Arduino.h"

enum Type { A_TYPE, B_TYPE, C_TYPE, NONE };

class GUI {

public:
  GUI(int TXDBT_PIN, int RXDBT_PIN);

  Type getType();
  bool checkDeposit();
  bool checkExtend();

  void sendConfirm();
  void sendTime(int sec);

  MsgServiceBT* msgService;
};

#endif
