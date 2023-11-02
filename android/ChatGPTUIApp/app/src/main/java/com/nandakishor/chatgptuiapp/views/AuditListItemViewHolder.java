package com.nandakishor.chatgptuiapp.views;

import android.view.View;
import android.widget.TextView;

import com.nandakishor.chatgptuiapp.R;
import com.nandakishor.chatgptuiapp.models.AuditListItemData;

import androidx.recyclerview.widget.RecyclerView;

public class AuditListItemViewHolder extends RecyclerView.ViewHolder {

  TextView seqNo;
  TextView prompt;
  TextView response;
  TextView dateTime;
  public AuditListItemViewHolder(View view) {
    super(view);
    seqNo = view.findViewById(R.id.seq_no);
    prompt = view.findViewById(R.id.prompt);
    response = view.findViewById(R.id.response);
    dateTime = view.findViewById(R.id.datetime);
  }

  public void bind(AuditListItemData data) {
    this.seqNo.setText(data.getSeqNo());
    this.prompt.setText(data.getPrompt());
    this.response.setText(data.getResponse());
    this.dateTime.setText(data.getDateTime());
  }

  public void setSeqNo(TextView seqNo) {
    this.seqNo = seqNo;
  }

  public void setPrompt(TextView prompt) {
    this.prompt = prompt;
  }

  public void setDateTime(TextView dateTime) {
    this.dateTime = dateTime;
  }

  public void setResponse(TextView response) {
    this.response = response;
  }

  public TextView getSeqNo() {
    return seqNo;
  }

  public TextView getDateTime() {
    return dateTime;
  }

  public TextView getPrompt() {
    return prompt;
  }

  public TextView getResponse() {
    return response;
  }
}
