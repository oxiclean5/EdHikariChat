package com.example.edhikarchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TalkActivity extends AppCompatActivity {
//    public static final String MESSAGES_CHILD = "messages";
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

        tid = getIntent().getStringExtra("id");
        tname = getIntent().getStringExtra("userName");

        Log.d(TAG, "tid: "+tid);
        Log.d(TAG, "tname: "+tname);
//        reference = FirebaseDatabase.getInstance().getReference();
//        ed_text = findViewById(R.id.ed_text);
//
//        tid = getIntent().getStringExtra("id");
////        tname = getIntent().getStringExtra("userName");
//
//        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Calendar c = Calendar.getInstance();
//                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM--dd hh:mm:ss");
//                String datetime = dateformat.format(c.getTime());
//
//                Chat chat = new Chat(ed_text.getText().toString(), tname, tphoto, null);
//                reference.child(MESSAGE_CHILD).push().setValue(chat);
//                ed_text.setText("");
//            }
//        });
//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setHasFixedSize(true);      //리사이클러뷰 강화
//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseUser = firebaseAuth.getCurrentUser();
//
//        if (firebaseUser != null) {
//                tname = getIntent().getStringExtra("userName");
//                if (firebaseUser.getPhotoUrl() != null) {
//                    tphoto = firebaseUser.getPhotoUrl().toString();
//                }
//
//            }
//
//
//        Query query = reference.child(MESSAGE_CHILD);
//        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
//                .setQuery(query, Chat.class).build();
//
//        firebaseAdapter = new FirebaseRecyclerAdapter<Chat, MessageViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(MessageViewHolder holder, int position, Chat model) {
//                holder.tv_chat.setText(model.getText());
//                holder.ttv_name.setText(model.getText());
//                if (model.getPhotoUrl() == null) {
//                    holder.tiv_profile.setImageDrawable(ContextCompat.getDrawable(TalkActivity.this,
//                            R.drawable.ic_baseline_face_24));
//                } else {
//                    Glide.with(TalkActivity.this).load(model.getPhotoUrl())
//                            .into(holder.tiv_profile);
//                }
//            }
//
//            @NonNull
//            @Override
//            public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.my_text_view, parent, false);
//                return new MessageViewHolder(view);
//            }
//        };
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(firebaseAdapter);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        firebaseAdapter.stopListening();
//    }
//}


//
//    private static final String TAG = MainActivity.class.getSimpleName();
//
//    private static final int REQUEST_INVITE = 1000;
//
//    public static final String MESSAGES_CHILD = "messages";
//    private DatabaseReference mFirebaseDatabaseReference;
//    private EditText mMessageEditText;
//
//    private FirebaseRecyclerAdapter<Chat, MessageViewHolder> mFirebaseAdapter;
//    private RecyclerView mMessageRecyclerView;
//
//    // Firebase 인스턴스 변수
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseUser mFirebaseUser;
//    private FirebaseRemoteConfig mFirebaseRemoteConfig;
//
//    private GoogleSignInClient mGoogleSignInClient;
//
//    // 사용자 이름과 사진
//    private String mUsername;
//    private String mPhotoUrl;
//
////    @Override
////    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
////        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
////    }
//
//    public static class MessageViewHolder extends RecyclerView.ViewHolder {
//        TextView ttv_name;
////        ImageView messageImageView;
//        TextView tv_chat;
//        CircleImageView tiv_profile;
//
//        public MessageViewHolder(View v) {
//            super(v);
//            ttv_name = itemView.findViewById(R.id.ttv_name);
//            tv_chat = itemView.findViewById(R.id.tv_chat);
//            tiv_profile = itemView.findViewById(R.id.tiv_profile);
////            messengerImageView = itemView.findViewById(R.id.messengerImageView);
//        }
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseUser = mFirebaseAuth.getCurrentUser();
//        if (mFirebaseUser == null) {
//            // 인증이 안 되었다면 인증 화면으로 이동
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//            return;
//        } else {
//            mUsername = mFirebaseUser.getDisplayName();
//            if (mFirebaseUser.getPhotoUrl() != null) {
//                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
//            }
//        }
//
//        mMessageRecyclerView = findViewById(R.id.recycler_view);
//
//        // GoogleApiClient 초기화
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        // Firebase 리얼타임 데이터 베이스 초기화
//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
//
//
//        mMessageEditText = findViewById(R.id.ed_text);
//        // 보내기 버튼
//        findViewById(R.id.bt_send).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Chat chatMessage = new Chat(mMessageEditText.getText().toString(),
//                        mUsername,
//                        mPhotoUrl,
//                        null);
//                mFirebaseDatabaseReference.child(MESSAGES_CHILD)
//                        .push()
//                        .setValue(chatMessage);
//                mMessageEditText.setText("");
//            }
//        });
//
//        // 쿼리 수행 위치
//        Query query = mFirebaseDatabaseReference.child(MESSAGES_CHILD);
//
//        // 옵션
//        FirebaseRecyclerOptions<Chat> options =
//                new FirebaseRecyclerOptions.Builder<Chat>()
//                        .setQuery(query, Chat.class)
//                        .build();
//
//        // 어댑터
//        mFirebaseAdapter = new FirebaseRecyclerAdapter<Chat, MessageViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(MessageViewHolder holder, int position, Chat model) {
//                holder.ttv_name.setText(model.getText());
//                holder.tv_chat.setText(model.getName());
//                if (model.getPhotoUrl() == null) {
//                    holder.tiv_profile.setImageDrawable(ContextCompat.getDrawable(TalkActivity.this,
//                            R.drawable.ic_baseline_face_24));
//                } else {
//                    Glide.with(TalkActivity.this)
//                            .load(model.getPhotoUrl())
//                            .into(holder.tiv_profile);
//                }
//            }
//
//            @Override
//            public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.my_text_view, parent, false);
//                return new MessageViewHolder(view);
//            }
//        };
//
//        // 리사이클러뷰에 레이아웃 매니저와 어댑터 설정
//        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mMessageRecyclerView.setAdapter(mFirebaseAdapter);
//
//        // Firebase Remote Config 초기화
//        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//
//        // Firebase Remote Config 설정
//        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
//                new FirebaseRemoteConfigSettings.Builder()
//                        .setDeveloperModeEnabled(true)
//                        .build();
//
//        // 인터넷 연결이 안 되었을 때 기본 값 정의
//        Map<String, Object> defaultConfigMap = new HashMap<>();
//        defaultConfigMap.put("message_length", 10L);
//
//        // 설정과 기본 값 설정
//        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
//        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);
//
//        // 원격 구성 가져오기
////        fetchConfig();
//
//        // 새로운 글이 추가되면 제일 하단으로 포지션 이동
//        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
//            @Override
//            public void onItemRangeInserted(int positionStart, int itemCount) {
//                super.onItemRangeInserted(positionStart, itemCount);
//                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
//                LinearLayoutManager layoutManager = (LinearLayoutManager) mMessageRecyclerView.getLayoutManager();
//                int lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
//
//                if (lastVisiblePosition == -1 ||
//                        (positionStart >= (friendlyMessageCount - 1) &&
//                                lastVisiblePosition == (positionStart - 1))) {
//                    mMessageRecyclerView.scrollToPosition(positionStart);
//                }
//            }
//        });
//
//        // 키보드 올라올 때 RecyclerView의 위치를 마지막 포지션으로 이동
//        mMessageRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (bottom < oldBottom) {
//                    v.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mMessageRecyclerView.smoothScrollToPosition(mFirebaseAdapter.getItemCount());
//                        }
//                    }, 100);
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // FirebaseRecyclerAdapter 실시간 쿼리 시작
//        mFirebaseAdapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        // FirebaseRecyclerAdapter 실시간 쿼리 중지
//        mFirebaseAdapter.stopListening();
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.sign_out_menu:
//                mGoogleSignInClient.signOut()
//                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                mFirebaseAuth.signOut();
//                                mUsername = "";
//                                startActivity(new Intent(TalkActivity.this, MainActivity.class));
//                                finish();
//                            }
//                        });
//                return true;
//            case R.id.invitation_menu:
//                sendInvitation();
//                return true;
//            case R.id.crash_menu:
//                throw new RuntimeException("치명적인 버그!!");
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    // 원격 구성 가져오기
//    public void fetchConfig() {
//        long cacheExpiration = 3600; // 1시간
//        // 개발자 모드라면 0초로 하기
//        if (mFirebaseRemoteConfig.getInfo().getConfigSettings()
//                .isDeveloperModeEnabled()) {
//            cacheExpiration = 0;
//        }
//        mFirebaseRemoteConfig.fetch(cacheExpiration)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // 원격 구성 가져오기 성공
//                        mFirebaseRemoteConfig.activateFetched();
//                        applyRetrievedLengthLimit();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // 원격 구성 가져오기 실패
//                        Log.w(TAG, "Error fetching config: " +
//                                e.getMessage());
//                        applyRetrievedLengthLimit();
//                    }
//                });
//    }


//    /**
//     * 서버에서 가져 오거나 캐시된 값을 가져 옴
//     */
//    private void applyRetrievedLengthLimit() {
//        Long messageLength =
//                mFirebaseRemoteConfig.getLong("message_length");
//        mMessageEditText.setFilters(new InputFilter[]{new
//                InputFilter.LengthFilter(messageLength.intValue())});
//        Log.d(TAG, "메시지 길이 : " + messageLength);
//    }
//
//    private void sendInvitation() {
//        Intent intent = new AppInviteInvitation.IntentBuilder("초대 제목")
//                .setMessage("채팅앱에 초대합니다")
//                .setCallToActionText("채팅에 참여하기")
//                .build();
//        startActivityForResult(intent, REQUEST_INVITE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
//
//        if (requestCode == REQUEST_INVITE) {
//            if (resultCode == RESULT_OK) {
//                // 얼마나 많은 초대를 보냈는지
//                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
//                Log.d(TAG, "Invitations sent: " + ids.length);
//            } else {
//                // 초대가 취소되거나 실패함
//                Log.d(TAG, "Failed to send invitation.");
//            }
//        }
//    }
//
//}




        database = FirebaseDatabase.getInstance();
        talkArrayList = new ArrayList<>();

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

    }
}