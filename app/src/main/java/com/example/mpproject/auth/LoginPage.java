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
//import com.example.mpprojectrecovery.home.MainActivity;
import com.example.mpproject.home.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {

    TextView GotoRegPageBtn;

    EditText LoginEmail,LoginPassword;

    Button LoginButton;

    String email,password;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        auth = FirebaseAuth.getInstance();

        GotoRegPageBtn = findViewById(R.id.goToRegPageBtn);

        LoginEmail = findViewById(R.id.loginEmail);
        LoginPassword = findViewById(R.id.loginPassword);

        LoginButton = findViewById(R.id.loginBtn);

        GotoRegPageBtn.setOnClickListener(v -> {

            Intent changePageIntent = new Intent(LoginPage.this,RegisterPage.class);
            startActivity(changePageIntent);

        });

        LoginButton.setOnClickListener(v -> {

            email = LoginEmail.getText().toString();
            password = LoginPassword.getText().toString();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(LoginPage.this,"Enter email",Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(LoginPage.this,"Enter password",Toast.LENGTH_SHORT).show();
                return;
            } else if(password.length() < 6){
                Toast.makeText(LoginPage.this,"Password length too short. Password must be 6 character long.",Toast.LENGTH_SHORT).show();
                return;

            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginPage.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                                Intent switchpage = new Intent(LoginPage.this, MainActivity.class);
                                startActivity(switchpage);
                            } else {
                                Toast.makeText(LoginPage.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

    }
}
