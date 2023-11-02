package com.nandakishor.chatgptuiapp.models;

public class AuditListItemData {
  String seqNo, prompt,response, dateTime;

  public AuditListItemData(String seqNo, String prompt, String response, String dateTime) {
    this.seqNo = seqNo;
    this.prompt = prompt;
    this.response = response;
    this.dateTime = dateTime;
  }

  public String getDateTime() {
    return dateTime;
  }

  public String getPrompt() {
    return prompt;
  }

  public String getResponse() {
    return response;
  }

  public String getSeqNo() {
    return seqNo;
  }

  public void setDateTime(String dateTime) {
    this.dateTime = dateTime;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  public void setSeqNo(String seqNo) {
    this.seqNo = seqNo;
  }
}
