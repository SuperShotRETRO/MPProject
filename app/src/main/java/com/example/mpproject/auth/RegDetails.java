package com.example.mpproject.auth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mpproject.R;
//import com.example.mpprojectrecovery.home.MainActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class RegDetails extends AppCompatActivity{

    EditText nameInput;

    Button dateBtn,submit;

    TextView dateView;

    String name,date,uid;

    HashMap<String,String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regdetails);

        user = new HashMap<>();

        dateView = findViewById(R.id.dateText);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        nameInput = findViewById(R.id.nameInput);

        dateBtn = findViewById(R.id.datePicker);
        submit = findViewById(R.id.regDetailsBtn);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegDetails.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(v -> {
            name = nameInput.getText().toString();
            user.put("name",name);
            user.put("date",date);

            FirebaseFirestore.getInstance().collection("user").document(uid).set(user);
//            Intent pageSwitch = new Intent(RegDetails.this, MainActivity.class);
//            startActivity(pageSwitch);
        });
    }

}
