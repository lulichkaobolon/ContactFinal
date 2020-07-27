package com.example.contactfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContactInfoEdit extends AppCompatActivity {
    EditText editEmail, editDate, editGender, editCity;
    TextView tv;
    Integer index = MainActivity.selectedID;
    Button btnOk, btnCancel;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info_edit);
        myDb = new DatabaseHelper(this);
        editEmail = findViewById(R.id.et_email);
        editDate = findViewById(R.id.et_date);
        editGender = findViewById(R.id.et_gender);
        editCity = findViewById(R.id.et_city);
        btnOk = findViewById(R.id.btn_ok_contact_info_edit);
        btnCancel = findViewById(R.id.btn_cancel_contact_info_edit);
        update();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void update() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDb.updateData(editEmail.getText().toString(),
                        editDate.getText().toString(),
                        editGender.getText().toString(),
                        editCity.getText().toString());
                if (isUpdated) {
                    Toast.makeText(ContactInfoEdit.this, "Updated", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ContactInfoEdit.this, "Not updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}