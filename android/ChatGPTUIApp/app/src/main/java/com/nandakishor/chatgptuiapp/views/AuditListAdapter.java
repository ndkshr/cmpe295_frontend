package com.nandakishor.chatgptuiapp.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nandakishor.chatgptuiapp.R;
import com.nandakishor.chatgptuiapp.models.AuditListItemData;
import com.nandakishor.chatgptuiapp.views.AuditListItemViewHolder;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AuditListAdapter extends RecyclerView.Adapter<AuditListItemViewHolder>{

  List<AuditListItemData> data = Collections.emptyList();

  public void setData(List<AuditListItemData> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  @NonNull
  @Override
  public AuditListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new AuditListItemViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.audit_list_item, parent, false)
    );
  }

  @Override
  public void onBindViewHolder(@NonNull AuditListItemViewHolder holder, int position) {
    holder.bind(data.get(position));
  }

  @Override
  public int getItemCount() {
    return data.size();
  }
}
