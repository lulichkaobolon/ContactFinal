package com.example.contactfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {
    EditText editName, editPhone;
    String inputName, inputPhone;
    int inputImage;
    DatabaseHelper dh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        dh = new DatabaseHelper(this);
        final Spinner spinner = findViewById(R.id.spinner);
        final SimpleImageArrayAdapter adapter = new SimpleImageArrayAdapter(this, MainActivity.icons);
        spinner.setAdapter(adapter);
        final Button btnOk = findViewById(R.id.button_ok_add_contact);
        final Button btnCancel = findViewById(R.id.button_cancel_add_contact);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputImage = (spinner.getSelectedItemPosition()>=0)
                        ? MainActivity.icons[spinner.getSelectedItemPosition()]
                        : R.drawable.icon_1;
                inputName = editName.getText().toString();
                inputPhone = editPhone.getText().toString();
                if(!inputPhone.equals("") && !inputName.equals("")) {
                    Intent intent = new Intent();
                    intent.putExtra(Line.keyName, inputName);
                    intent.putExtra(Line.keyPhone, inputPhone);
                    intent.putExtra(Line.keyImage, inputImage);
                    boolean isInserted = dh.insertData(MainActivity.lines.size(),"","","","");
                    if(isInserted) {
                        Toast.makeText(AddContact.this, "Inserted", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(AddContact.this, "Not inserted", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(AddContact.this, "Fill both fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

}