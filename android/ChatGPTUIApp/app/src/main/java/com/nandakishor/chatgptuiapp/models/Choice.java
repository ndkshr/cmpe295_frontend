package com.nandakishor.chatgptuiapp.models;

import java.util.List;

public class Choice {

  Message message;
  public Choice(Message msg) {
    message = msg;
  }

  public Message getMessage() {
    return message;
  }
}
