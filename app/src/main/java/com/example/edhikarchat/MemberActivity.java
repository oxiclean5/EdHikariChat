package com.example.edhikarchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.edhikarchat.ui.notifications.ProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Hashtable;

public class MemberActivity extends AppCompatActivity {
    private static final String TAG = "MemberActivity";
    private EditText member_id;
    private EditText member_pwd;
    private EditText member_name;
    private Button member_join;
    private EditText member_pwdcheck;
    ProgressBar progressbar;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    Intent talk_in;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_member);
//
//        //버튼 등록
//        progressbar = (ProgressBar)findViewById(R.id.progressbar);
//        member_id = (EditText) findViewById(R.id.member_id);
//        member_pwd = (EditText) findViewById(R.id.member_pwd);
//        member_name = (EditText) findViewById(R.id.member_name);
//        member_join = (Button) findViewById(R.id.member_join);
//
////        //가입정보 가져오기
////        String id = member_id.getText().toString();
////        String pwd = member_pwd.getText().toString();
////        String name = member_name.getText().toString();
//
//        member_join.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //가입정보 가져오기
//                String id = member_id.getText().toString().trim();
//                String pwd = member_pwd.getText().toString().trim();
////                String name = member_name.getText().toString().trim();
//
//        if (id.isEmpty()) {
//            Toast.makeText(MemberActivity.this, "아이디를 입력해주세요", Toast.LENGTH_LONG).show();
//            return;
//        }
//        if (pwd.isEmpty()) {
//            Toast.makeText(MemberActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
//            return;
//        }
////        if (name.isEmpty()) {
////            Toast.makeText(MemberActivity.this, "이름을 입력해주세요", Toast.LENGTH_LONG).show();
////            return;
////        }
////        progressbar.incrementProgressBy(5);
//
////              Toast.makeText(MainActivity.this,"Email : "+ id+",Password : "+ pwd, Toast.LENGTH_LONG).show();
//      //신규계정 등록하기
//        mAuth.createUserWithEmailAndPassword(id, pwd).addOnCompleteListener(MemberActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        progressbar.setVisibility(View.GONE);
//
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(MemberActivity.this, "회원가입 성공",
//                                    Toast.LENGTH_SHORT).show();
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            String id = user.getEmail();
//                            String name = member_name.getText().toString();
////                                    updateUI(user);
//
//                            DatabaseReference myRef = database.getReference("User").child(user.getUid());
//
//                            Hashtable<String, String> numbers
//                                    = new Hashtable<String, String>();
//                            numbers.put("id", id);
//                            numbers.put("userName", name);
//                            myRef.setValue(numbers);
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(MemberActivity.this, "아이디 형식이 잘못되었거나 이미 있는 아이디입니다.",
//                                    Toast.LENGTH_SHORT).show();
////                                    updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//
//            }
//        });
//
//    }
//    public boolean onSupportNavigateUp(){
//        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
//        return super.onSupportNavigateUp(); // 뒤로가기 버튼
//    }
//}


//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
////            updateUI(currentUser);
//    }
//}
private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        //액션 바 등록하기
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");

        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼
        actionBar.setDisplayShowHomeEnabled(true); //홈 아이콘

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth =  FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance().getReference();


        //        progressbar = (ProgressBar)findViewById(R.id.progressbar);
        member_id = (EditText) findViewById(R.id.member_id);
        member_pwd = (EditText) findViewById(R.id.member_pwd);
        member_pwdcheck = (EditText) findViewById(R.id.member_pwdcheck);
        member_name = (EditText) findViewById(R.id.member_name);
        member_join = (Button) findViewById(R.id.member_join);

        //파이어베이스 user 로 접글

        //가입버튼 클릭리스너   -->  firebase에 데이터를 저장한다.
        member_join.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //가입 정보 가져오기
                final String email = member_id.getText().toString().trim();
                String pwd = member_pwd.getText().toString().trim();
                String pwdcheck = member_pwdcheck.getText().toString().trim();


                if (pwd.equals(pwdcheck)) {
                    Log.d(TAG, "등록 버튼 " + email + " , " + pwd);
                    final ProgressDialog mDialog = new ProgressDialog(MemberActivity.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();

                    //파이어베이스에 신규계정 등록하기
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(MemberActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //가입 성공시
                            if (task.isSuccessful()) {
                                mDialog.dismiss();

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();
                                String uid = user.getUid();
                                String name = member_name.getText().toString().trim();

                                //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                HashMap<Object, String> hashMap = new HashMap<>();

                                hashMap.put("uid", uid);
                                hashMap.put("id", email);
                                hashMap.put("userName", name);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("User");
                                reference.child(uid).setValue(hashMap);


                               // 가입시 Talk엑티비티에 이름 정보를 보냄
                                talk_in = new Intent(MemberActivity.this, TalkActivity.class);
                                talk_in.putExtra("name",name);
                                Intent profile_in = new Intent(MemberActivity.this, ProfileFragment.class);
                                profile_in.putExtra("uid",uid);
                                startActivity(profile_in);

                                //가입이 이루어져을시 가입 화면을 빠져나감.
                                Intent intent = new Intent(MemberActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(MemberActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(MemberActivity.this, "이미 존재하는 아이디이거나 잘못된 형식입니다.", Toast.LENGTH_SHORT).show();
                                return;  //해당 메소드 진행을 멈추고 빠져나감.

                            }

                        }
                    });

                    //비밀번호 오류시
                } else {
                    Toast.makeText(MemberActivity.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }
}