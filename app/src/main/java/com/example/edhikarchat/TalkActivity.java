package com.example.edhikarchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class TalkActivity extends AppCompatActivity {

    FirebaseDatabase database;
    String tname;
    String ttext;
    String tid;
    EditText ed_text;
    Button bt_send;
    private static final String TAG = "TalkActivity";
    MyAdapter myAdapter;
    RecyclerView recyclerView;
    ArrayList<Chat> talkArrayList;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        tid = getIntent().getStringExtra("id");
        tname = getIntent().getStringExtra("userName");
//        bt_send = (Button) findViewById(R.id.bt_send);
//        ed_text = (EditText) findViewById(R.id.ed_text);
//
//        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        String [] myDataset = {"test1","test2","test3","test4"};
//        myAdapter = new MyAdapter(myDataset);
//        recyclerView.setAdapter(myAdapter);
//
//        ChildEventListener childEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
//
//                // A new comment has been added, add it to the displayed list
//                Chat chat = dataSnapshot.getValue(Chat.class);
//                String tname = chat.getName();
//                String ttext = chat.getText();
//                Log.d(TAG, "tname: "+tname);
//                Log.d(TAG, "ttext: "+ttext);
//                // ...
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
//                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());
//
//                // A comment has changed, use the key to determine if we are displaying this
//                // comment and if so displayed the changed comment.
//                Chat chat = dataSnapshot.getValue(Chat.class);
//                String commentKey = dataSnapshot.getKey();
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
//        ref.addChildEventListener(childEventListener);
//
//
//        bt_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String sttext = ed_text.getText().toString();
//                Toast.makeText(TalkActivity.this, "MSG : "+sttext,Toast.LENGTH_SHORT).show();
////                tname = getIntent().getStringExtra("userName");
//                ttext = ed_text.getText().toString();
//                database = FirebaseDatabase.getInstance();
//
//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM--dd hh:mm:ss");
//                String datetime = dateformat.format(c.getTime());
//
//
//                DatabaseReference myref = database.getReference("message").child(datetime);
//
//                Hashtable<String, String> numbers = new Hashtable<String, String>();
//                numbers.put("name",tname);
//                numbers.put("text",ttext);
//                myref.setValue(numbers);
//            }
//        });

        database = FirebaseDatabase.getInstance();
        talkArrayList = new ArrayList<>();
//        tname = getIntent().getStringExtra("userName");

        ed_text = (EditText)findViewById(R.id.ed_text);
        bt_send = (Button)findViewById(R.id.bt_send);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(talkArrayList, tid);
        recyclerView.setAdapter(myAdapter);
        Log.d(TAG, "onCreate: called");



        ChildEventListener childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Chat chat = dataSnapshot.getValue(Chat.class);
                String commentKey = dataSnapshot.getKey();
                String tid = chat.getId();
                String tname = chat.getName();
                String ttext = chat.getText();
                Log.d(TAG, "tid: "+tid);
                Log.d(TAG, "ttext: "+ttext);
                talkArrayList.add(chat);
                myAdapter.notifyDataSetChanged();
                // ...
            }



            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
//                Chat chat = dataSnapshot.getValue(Chat.class);
//                String commentKey = dataSnapshot.getKey();


                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
//                Comment movedComment = dataSnapshot.getValue(Comment.class);
//                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                Toast.makeText(TalkActivity.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        DatabaseReference ref = database.getReference("message");

        ref.addChildEventListener(childEventListener);


        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tname = getIntent().getStringExtra("userName");
                ttext = ed_text.getText().toString();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM--dd hh:mm:ss");
                String datetime = dateformat.format(c.getTime());


                DatabaseReference myRef = database.getReference("message").child(datetime);

                Hashtable<String, String> numbers = new Hashtable<String, String>();
                numbers.put("id",tid);
                numbers.put("text",ttext);
                numbers.put("name",tname);
                myRef.setValue(numbers);

            }
        });
//        tname = getIntent().getStringExtra("name");
//        ttext = ed_text.getText().toString();
//       database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//       Hashtable<String, String> numbers = new Hashtable<String, String>();
//       numbers.put("name",tname);
//       numbers.put("text",ttext);
    }
}