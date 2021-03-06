package com.example.edhikarchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements View.OnClickListener {
    private OnNoteListener mOnNoteListener;
    List<User> arraylist;
    String stEmail;
    Context context;

    @Override
    public void onClick(View v) {

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView tv_id;
        public ImageView iv_profile;
        public TextView tv_name;
        public TextView tv_message;
        OnNoteListener onNoteListener;

        public ViewHolder(View itemView,  OnNoteListener onNoteListener) {
            super(itemView);
            tv_id = (TextView) itemView.findViewById(R.id.tv_id);
            iv_profile = (ImageView)itemView.findViewById(R.id.iv_profile);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserAdapter(List<User> arraylist, Context context, OnNoteListener onNoteListener) {
        this.arraylist = arraylist;
        this.context = context;
        this.mOnNoteListener = onNoteListener;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v,mOnNoteListener);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.tv_id.setText(arraylist.get(position).getId());
        holder.tv_name.setText(String.valueOf(arraylist.get(position).getUserName()));
        holder.tv_message.setText(arraylist.get(position).getStatusmessage());
        Glide.with(holder.itemView)
                .load(arraylist.get(position).getProfile())
                .into(holder.iv_profile);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int postion);
    }
}



