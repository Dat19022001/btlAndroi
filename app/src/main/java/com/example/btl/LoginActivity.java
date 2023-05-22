package com.example.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText Username, password;
    TextView forgotPassword, Signup;
    Button SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Username = findViewById(R.id.UserName);
        password = findViewById(R.id.Password);
        forgotPassword = findViewById(R.id.forgotPass);
        Signup = findViewById(R.id.signUp);
        SignIn = findViewById(R.id.signIn);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password1 = intent.getStringExtra("password");
        Username.setText(email);
        password.setText(password1);
        setOnclick();
    }

    private void setOnclick() {
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ResgiterActivity.class);
                startActivity(intent);

            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgotPassActivity.class);
                intent.putExtra("email",Username.getText().toString().trim());
                Log.d("test", Username.getText().toString());
                if(Username.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhap Username", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(intent);
                }
            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIN();
            }
        });
    }

    private void onClickSignIN() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String username1 = Username.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(username1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại vui lòng kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();

                }
            }
        });
//        auth.signInWithEmailAndPassword(username1, password1)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                            startActivity(intent);
//                        } else {
//                            // If sign in fails, display a message to the user.
//
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
    }

}