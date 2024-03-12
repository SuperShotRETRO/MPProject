package com.example.mpproject.auth;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mpproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPage extends AppCompatActivity {

    TextView GotoLoginPageBtn;

    EditText RegEmail,RegPassword;

    Button RegButton;

    String email,password;

    String uid;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        auth = FirebaseAuth.getInstance();

        GotoLoginPageBtn = findViewById(R.id.goToLoginPageBtn);

        RegEmail = findViewById(R.id.regEmail);
        RegPassword = findViewById(R.id.regPassword);

        RegButton = findViewById(R.id.regBtn);

        GotoLoginPageBtn.setOnClickListener(v -> {

            Intent changePageIntent = new Intent(RegisterPage.this,LoginPage.class);
            startActivity(changePageIntent);

        });

        RegButton.setOnClickListener(v -> {

            email = RegEmail.getText().toString();
            password = RegPassword.getText().toString();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(RegisterPage.this,"Enter email",Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(RegisterPage.this,"Enter password",Toast.LENGTH_SHORT).show();
                return;
            } else if(password.length() < 6){
                Toast.makeText(RegisterPage.this,"Password length too short. Password must be 6 character long.",Toast.LENGTH_SHORT).show();
                return;

            }

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterPage.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                        uid = auth.getUid();
                        Intent pageSwitch = new Intent(RegisterPage.this,RegDetails.class);
                        pageSwitch.putExtra("uid",uid);
                        startActivity(pageSwitch);
                    }
                    else {
                        Toast.makeText(RegisterPage.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });
        });
    }
}

