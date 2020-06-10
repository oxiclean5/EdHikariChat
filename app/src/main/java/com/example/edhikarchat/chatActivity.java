//chat 클래스

        package com.example.edhikarchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Hashtable;

public class chatActivity extends AppCompatActivity {
    private static final String TAG = "chatActivity";


    private RecyclerView recyclerView;                    //리사이클러 뷰, 어댑터, 레이아웃메니저저
    //   MyAdapter mAdapter;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private ArrayList<User> arrayList;
    FirebaseDatabase database;

    Button bt_finish;
    EditText ed_text;
    Button bt_send;
    String stid;
    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Flag_talking flag_talking;
    private Flag_call flag_call;
    private Flag_friend flag_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.action_friend:
                        setFrag(0);
                        break;
                    case R.id.action_taking:
                        setFrag(1);
                        break;
                    case R.id.action_call:
                        setFrag(2);
                        break;

                }
                return true;
            }
        });
        flag_friend = new Flag_friend();
        flag_talking = new Flag_talking();
        flag_call = new Flag_call();

        setFrag(0); // 첫 프래그 호출

        stid = getIntent().getStringExtra("id");
//        bt_finish=(Button)findViewById(R.id.bt_finish);     // 뒤로가기(로그인 창으로 돌아가기)
//        bt_finish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

//        bt_send = (Button)findViewById(R.id.bt_send);      // 메세지 보내기

//        ed_text = (EditText)findViewById(R.id.ed_text);   // 텍스트

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);   // 리사이클 뷰
        recyclerView.setHasFixedSize(true);                                // 리사이클러뷰 성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();                //User 객체를 담을 어레이리스트(어댑터쪽으로)

        database = FirebaseDatabase.getInstance();    // 파이어베이스 연동

        databaseReference = database.getReference("User");  // DB테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 부분
                arrayList.clear(); // 기존 배열 초기화
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){  // 데이터 리스트 추출
                    User user = snapshot.getValue(User.class);   // User 객체에 데이터 담기
                    arrayList.add(user);   // 배열에 담은 데이터를 리사이클러 뷰로 보낼 준비

                }
                adapter.notifyDataSetChanged();  //리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던 중 error 발생 시
                Log.e(TAG, "chatActivity: ",databaseError.toException() );
            }
        });


        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);  // 리사이클러 뷰에 어댑터 연결
//        ref.addChildEventListener(childEventListener);      위에 주석 해제할 때 같이 해제할 것




//        bt_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = ed_text.getText().toString();
////                Toast.makeText(chatActivity.this, "MSG : "+text, Toast.LENGTH_SHORT).show();
//
//                // Write a message to the database
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("message");
//
//                Hashtable<String, String> numbers
//                        = new Hashtable<String, String>();
//                numbers.put("id", stid);
//                numbers.put("text", text);
//                myRef.setValue(numbers);
//
//            }
//        });

    }
    // 프래그먼트 교체 실행부분
    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:
                ft.replace(R.id.main_frame,flag_friend);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame,flag_talking);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame,flag_call);
                ft.commit();
                break;
        }
    }
}