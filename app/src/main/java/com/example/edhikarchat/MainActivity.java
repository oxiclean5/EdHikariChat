package com.example.edhikarchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    Button main_login;
    Intent chat_in;
    Intent member_in;
    EditText main_id;
    EditText main_pwd;
    Button main_create;
    ProgressBar progressbar;
    SignInButton main_googlelogin;

    FirebaseDatabase database;
    Intent talk_in;
    //    private static final String TAG = MainActivity.class.getSimpleName();
//    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 1000;

    // Firebase 인스턴스 변수
    private FirebaseAuth mFirebaseAuth;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressbar = (ProgressBar) findViewById(R.id.progressbar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //버튼 등록하기
        main_id = (EditText) findViewById(R.id.main_id);
        main_pwd = (EditText) findViewById(R.id.main_pwd);
        main_login = (Button) findViewById(R.id.main_login);
        main_create = (Button) findViewById(R.id.main_create);
        // 로그인 버튼 클릭시
        main_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String stid = main_id.getText().toString();
                String stpwd = main_pwd.getText().toString();
                if (stid.isEmpty()) {
                    Toast.makeText(MainActivity.this, "아이디를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (stpwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(stid, stpwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String UserId = user.getEmail();
                                    String UserName = user.getDisplayName();
                                    Log.d(TAG, "UserId: " + UserId + ", UserName: " + UserName);

                                    SharedPreferences sharedPref = getSharedPreferences("shared", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("UserId : ", UserId);
                                    editor.commit();

                                    chat_in = new Intent(MainActivity.this, TabActivity.class);
                                    chat_in.putExtra("id", stid);
                                    startActivity(chat_in);

                        talk_in = new Intent(MainActivity.this, TalkActivity.class);
                        talk_in.putExtra("id",stid);


//                        updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                        updateUI(null);
                                }

                                // ...
                            }
                        });
//                Toast.makeText(MainActivity.this,"Login",Toast.LENGTH_LONG).show();

            }
        });

        //회원가입 버튼 클릭시
        main_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member_in = new Intent(MainActivity.this, MemberActivity.class);
                startActivity(member_in);
            }

        });

        //구글 로그인
//        main_googlelogin = (SignInButton) findViewById(R.id.main_googlelogin);
////            findViewById(R.id.main_googlelogin).setOnClickListener(this);
//
//            // FirebaseAuth 초기화
//            mFirebaseAuth = FirebaseAuth.getInstance();
//
//            // GoogleApiClient 초기화
//            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken(getString(R.string.default_web_client_id))
//                    .requestEmail()
//                    .build();
////            mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        }
//
//
//
//        @Override
//        public void onClick(View view) {
//            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//            startActivityForResult(signInIntent, RC_SIGN_IN);
//        }
//
//        @Override
//        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//            Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            // 로그인 결과
//            if (requestCode == RC_SIGN_IN) {
//                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//                try {
//                    // 구글 로그인에 성공하면 파이어베이스에 인증
//                    GoogleSignInAccount account = task.getResult(ApiException.class);
//                    firebaseAuthWithGoogle(account);
//                } catch (ApiException e) {
//                    // 구글 로그인 실패
//                    Log.w(TAG, "Google sign in failed", e);
//                }
//            }
//        }
//
//        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//            Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
//            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//            mFirebaseAuth.signInWithCredential(credential)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
//
//                            // 인증에 성공하면 MainActivity 로 이동, 실패하면 에러 메시지 표시
//                            if (!task.isSuccessful()) {
//                                Log.w(TAG, "signInWithCredential", task.getException());
//                                Toast.makeText(MainActivity.this, "Authentication failed.",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//                                startActivity(new Intent(MainActivity.this, TabActivity.class));
//                                finish();
//                            }
//                        }
//                    });
    }
}

