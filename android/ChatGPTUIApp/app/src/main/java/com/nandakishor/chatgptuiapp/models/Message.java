package com.nandakishor.chatgptuiapp.models;

public class Message {
  String role;
  String content;

  public Message(String r, String c) {
    role = r;
    content = c;
  }

  public String getRole() {
    return role;
  }

  public String getContent() {
    return content;
  }
}
