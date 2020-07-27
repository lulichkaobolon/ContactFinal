package com.example.contactfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactInfo extends AppCompatActivity {
    Button btnShow, btnEdit, btnBack, btnGetAll;
    DatabaseHelper databaseHelper;
    Integer viewCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        btnShow = findViewById(R.id.button_show_info);
        btnEdit = findViewById(R.id.button_edit_info);
        btnBack = findViewById(R.id.button_back_contact_info);
        btnGetAll = findViewById(R.id.button_get_all);
        databaseHelper = new DatabaseHelper(this);
        viewContact();
        viewAll();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(ContactInfo.this, ContactInfoEdit.class);
                startActivity(i1);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.selectedID = -1;
                finish();
            }
        });

    }

    public void viewAll() {
        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = databaseHelper.getAllData();
                StringBuilder builder = new StringBuilder();
                while (res.moveToNext()) {
                    builder.append("ID: ").append(res.getString(0)).append("\n");
                    builder.append("Email: ").append(res.getString(1)).append("\n");
                    builder.append("Date: ").append(res.getString(2)).append("\n");
                    builder.append("Gender: ").append(res.getString(3)).append("\n");
                    builder.append("City: ").append(res.getString(4)).append("\n");
                }
                showMessage("Data", builder.toString());
            }
        });
    }
    public void viewContact() {

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = databaseHelper.getAllData();
//                if (res.getCount() == 0) {
//                    showMessage("Error", "Nothing has been found!");
//                    return;
//                }
                StringBuilder builder = new StringBuilder();
                while (res.moveToNext()) {
                    if (MainActivity.selectedID == viewCounter) {
                        builder.append("ID: ").append(res.getString(0)).append("\n");
                        builder.append("Email: ").append(res.getString(1)).append("\n");
                        builder.append("Date: ").append(res.getString(2)).append("\n");
                        builder.append("Gender: ").append(res.getString(3)).append("\n");
                        builder.append("City: ").append(res.getString(4)).append("\n");
                    }
                    viewCounter++;
                }
                showMessage("Data", builder.toString());
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}