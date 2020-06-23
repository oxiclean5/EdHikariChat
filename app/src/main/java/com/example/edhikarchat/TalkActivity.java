package com.example.edhikarchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

import de.hdodenhof.circleimageview.CircleImageView;

public class TalkActivity extends AppCompatActivity {
    public static final String MESSAGES_CHILD = "messages";
    public static final String MESSAGE_CHILD = "message";
    private DatabaseReference reference;
    private FirebaseRecyclerAdapter<Chat, MessageViewHolder> firebaseAdapter;

    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;      //파이어베이스 인증
    private FirebaseUser firebaseUser;

    String tname;
    String ttext;
    String tid;
    String tphoto;
    EditText ed_text;
    Button bt_send;
    private static final String TAG = "TalkActivity";
    MyAdapter myAdapter;
    RecyclerView recyclerView;
    ArrayList<Chat> talkArrayList;
    private RecyclerView.LayoutManager layoutManager;


    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView ttv_name;
        TextView tv_chat;
        CircleImageView tiv_profile;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            ttv_name = itemView.findViewById(R.id.ttv_name);
            tv_chat = itemView.findViewById(R.id.tv_chat);
            tiv_profile = itemView.findViewById(R.id.tiv_profile);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);

        reference = FirebaseDatabase.getInstance().getReference();
        ed_text = findViewById(R.id.ed_text);

        tid = getIntent().getStringExtra("id");
        tname = getIntent().getStringExtra("userName");

        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM--dd hh:mm:ss");
                String datetime = dateformat.format(c.getTime());

                Chat chat = new Chat (ed_text.getText().toString(), tname, tphoto,null);
                reference.child(MESSAGE_CHILD).push().setValue(chat);
                ed_text.setText("");
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);      //리사이클러뷰 강화
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            startActivity(new Intent(this, MemberActivity.class));
            finish();
            return;
        } else{
            tname = firebaseUser.getDisplayName();
            if(firebaseUser.getPhotoUrl() != null){
                tphoto = firebaseUser.getPhotoUrl().toString();
            }

        }
        Query query = reference.child(MESSAGE_CHILD);
        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class).build();

        firebaseAdapter = new FirebaseRecyclerAdapter<Chat, MessageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(MessageViewHolder holder, int position, Chat chat) {
                holder.tv_chat.setText(chat.getText());
                holder.ttv_name.setText(chat.getText());
                if(chat.getPhotoUrl()==null){

                }
            }

            @NonNull
            @Override
            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_text_view, parent, false);
                return new MessageViewHolder(view);
            }
        };

//
//
//        database = FirebaseDatabase.getInstance();
//        talkArrayList = new ArrayList<>();
////        tname = getIntent().getStringExtra("userName");
//
//        ed_text = (EditText)findViewById(R.id.ed_text);
//        bt_send = (Button)findViewById(R.id.bt_send);
//
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        myAdapter = new MyAdapter(talkArrayList, tid);
//        recyclerView.setAdapter(myAdapter);
//        Log.d(TAG, "onCreate: called");
//
//
//
//        ChildEventListener childEventListener = new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//
//                // A new comment has been added, add it to the displayed list
//                Chat chat = dataSnapshot.getValue(Chat.class);
//                String commentKey = dataSnapshot.getKey();
//                String tid = chat.getId();
//                String tname = chat.getName();
//                String ttext = chat.getText();
//                Log.d(TAG, "tid: "+tid);
//                Log.d(TAG, "ttext: "+ttext);
//                talkArrayList.add(chat);
//                myAdapter.notifyDataSetChanged();
//                // ...
//            }
//
//
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
////                Chat chat = dataSnapshot.getValue(Chat.class);
////                String commentKey = dataSnapshot.getKey();
//
//
//                // ...
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so remove it.
//                String commentKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
//
//                // A comment has changed position, use the key to determine if we are
//                // displaying this comment and if so move it.
////                Comment movedComment = dataSnapshot.getValue(Comment.class);
////                String commentKey = dataSnapshot.getKey();
//
//                // ...
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
//                Toast.makeText(TalkActivity.this, "Failed to load comments.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        };
//        DatabaseReference ref = database.getReference("message");
//
//        ref.addChildEventListener(childEventListener);
//
//
//        bt_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                tname = getIntent().getStringExtra("userName");
//                ttext = ed_text.getText().toString();
//
//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM--dd hh:mm:ss");
//                String datetime = dateformat.format(c.getTime());
//
//
//                DatabaseReference myRef = database.getReference("message").child(datetime);
//
//                Hashtable<String, String> numbers = new Hashtable<String, String>();
//                numbers.put("id",tid);
//                numbers.put("text",ttext);
//                numbers.put("name",tname);
//                myRef.setValue(numbers);
//
//            }
//        });

    }
}