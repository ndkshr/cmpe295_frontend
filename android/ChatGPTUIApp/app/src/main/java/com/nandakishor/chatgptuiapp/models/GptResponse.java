package com.nandakishor.chatgptuiapp.models;

import java.util.List;

public class GptResponse {
  public List<Choice> choices;

  public GptResponse(List<Choice> ch) {
    choices = ch;
  }

  public List<Choice> getChoices() {
    return choices;
  }
}
