package com.example.edhikarchat;//package com.example.edhikarchat;
//
//import android.content.Context;
//import android.net.Uri;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import android.support.v7.widget.RecyclerView;


/**
 * Created by KPlo on 2018. 10. 28..
 */

//public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.MyViewHolder> {
//    private List<TalkData> mDataset;
//    private String myNickName;
//    // Provide a reference to the views for each data item
//    // Complex data items may need more than one view per item, and
//    // you provide access to all the views for a data item in a view holder
//    public static class MyViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public TextView Tv_name;
//        public TextView Tv_msg;
//        public View rootView;
//        public MyViewHolder(View v) {
//            super(v);
//            Tv_name = v.findViewById(R.id.Tv_name);
//            Tv_msg = v.findViewById(R.id.Tv_msg);
//            rootView = v;
//
//        }
//
//
//    }
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public TalkAdapter(List<TalkData> myDataset, Context context, String myNickName) {
//        //{"1","2"}
//        mDataset = myDataset;
//        this.myNickName = myNickName;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public TalkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
//                                                       int viewType) {
//        // create a new view
//        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_talk, parent, false);
//
//        MyViewHolder vh = new MyViewHolder(v);
//        return vh;
//    }
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        TalkData talk = mDataset.get(position);
//
//        holder.Tv_name.setText(talk.getNickname());
//        holder.Tv_msg.setText(talk.getMsg());
//
//        if(talk.getNickname().equals(this.myNickName)) {
//            holder.Tv_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//            holder.Tv_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
//        }
//        else {
//            holder.Tv_msg.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//            holder.Tv_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//        }
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//
//        //삼항 연산자
//        return mDataset == null ? 0 :  mDataset.size();
//    }
//
//    public TalkData getChat(int position) {
//        return mDataset != null ? mDataset.get(position) : null;
//    }
//
//    public void addChat(TalkData chat) {
//        mDataset.add(chat);
//        notifyItemInserted(mDataset.size()-1); //갱신
//    }
//
//}