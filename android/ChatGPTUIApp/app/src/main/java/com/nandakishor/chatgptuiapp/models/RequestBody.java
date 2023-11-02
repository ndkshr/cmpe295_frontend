package com.nandakishor.chatgptuiapp.models;

import java.util.List;

public class RequestBody {
  String model;
  List<Message> messages;
  double temperature;

  public RequestBody(String m, List<Message> msgs, double temp) {
    model = m;
    messages = msgs;
    temperature = temp;
  }
}
