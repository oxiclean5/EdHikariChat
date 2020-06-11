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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;
     Button main_login;
     Intent in;
     EditText main_id;
     EditText main_pwd;
     Button main_create;
     ProgressBar progressbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressbar = (ProgressBar)findViewById(R.id.progressbar);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        main_id = (EditText) findViewById(R.id.main_id);
        main_pwd = (EditText) findViewById(R.id.main_pwd);

        main_login = (Button) findViewById(R.id.main_login);
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
                        Log.d(TAG, "UserId: "+UserId+", UserName: "+UserName);

                        SharedPreferences sharedPref = getSharedPreferences("shared",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("UserId : ",UserId);
                        editor.commit();

                        in = new Intent(MainActivity.this, TabActivity.class);
                        in.putExtra("id",stid);
                        startActivity(in);
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
        main_create = (Button) findViewById(R.id.main_create);
        main_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = main_id.getText().toString();
                String pwd = main_pwd.getText().toString();
                if (id.isEmpty()) {
                    Toast.makeText(MainActivity.this, "아이디를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);

//              Toast.makeText(MainActivity.this,"Email : "+ id+",Password : "+ pwd, Toast.LENGTH_LONG).show();
                mAuth.createUserWithEmailAndPassword(id, pwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(MainActivity.this, "Authentication success.",
                                            Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
    }
        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
//            updateUI(currentUser);

    }

}