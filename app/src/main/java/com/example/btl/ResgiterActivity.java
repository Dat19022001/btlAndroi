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
import com.example.btl.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class ResgiterActivity extends AppCompatActivity {

    EditText email,password,confrimPass,Username;
    Button SignUp;
    TextView SignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgiter);

        Username = findViewById(R.id.Username);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        confrimPass = findViewById(R.id.ConfirmPassword);
        SignUp = findViewById(R.id.signUp);
        SignIn = findViewById(R.id.signIn);
        setOnclick();
    }

    private void setOnclick() {
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });




        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResgiterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onClickSignUp() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String email1 = email.getText().toString().trim();
        String username1 = Username.getText().toString().trim();
        String password1 =  password.getText().toString().trim();
        String password2 = confrimPass.getText().toString().trim();
        if (password1.equals(password2)){
            firebaseAuth.createUserWithEmailAndPassword(email1, password1)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User user = new User(email1,username1, password1,false);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(ResgiterActivity.this, "Chúc mừng bạn đã đăng kí thành công!", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ResgiterActivity.this,LoginActivity.class);
                                            intent.putExtra("email",email1);
                                            intent.putExtra("password",password1);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(ResgiterActivity.this, "Đăng kí thất bại, vui lòng thử lại!", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {

                            Toast.makeText(ResgiterActivity.this, "Đăng kí thất bại, vui lòng thử lại!", Toast.LENGTH_LONG).show();
                        }
                    });
        }else {
            Toast.makeText(ResgiterActivity.this, "The confirmation password is incorrect!", Toast.LENGTH_LONG).show();
        }

    }
}