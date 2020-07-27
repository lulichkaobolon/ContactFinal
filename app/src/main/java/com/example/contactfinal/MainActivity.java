package com.example.contactfinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int addContactID = 1;
    public static final int editContactID = 2;
    public static int selectedID = -1;
    TextView name, phone;
    ListView listView;
    ImageView image;
    DatabaseHelper databaseHelper;
    public static ArrayList<Line>lines = new ArrayList<>();
    public static Integer[] icons = {R.drawable.icon_1, R.drawable.icon_2,
            R.drawable.icon_3, R.drawable.icon_4,
            R.drawable.icon_5, R.drawable.icon_6,
            R.drawable.icon_7, R.drawable.icon_8};
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        image = findViewById(R.id.image);
        listView = findViewById(R.id.listView);
        final Button btnAdd = findViewById(R.id.add);
        final Button btnEdit = findViewById(R.id.edit);
        final Button btnDelete = findViewById(R.id.delete);
        databaseHelper = new DatabaseHelper(this);

        fillList();
        updateList();

        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                selectedID = position;
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedID = position;
                Intent i = new Intent(MainActivity.this, ContactInfo.class);
                startActivity(i);
                return true;
            }
        });


    }
    public void updateList () {
        CustomAdapter ca = new CustomAdapter(lines, this.getLayoutInflater());
        listView.setAdapter(ca);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add: {
                Intent i = new Intent(this, AddContact.class);
                startActivityForResult(i, addContactID);
                break;
            }
            case R.id.edit: {
                if(selectedID >= 0 && selectedID < lines.size()) {
                    Intent i = new Intent(this, EditContact.class);
                    String extraName = lines.get(selectedID).name;
                    String extraPhone = lines.get(selectedID).phone;
                    int extraImage = imgSearch(lines.get(selectedID).id);
                    i.putExtra(Line.keyName, extraName);
                    i.putExtra(Line.keyPhone, extraPhone);
                    i.putExtra(Line.keyImage, extraImage);
                    startActivityForResult(i, editContactID);

                }
                else {
                    Toast.makeText(this, "Choose the contact!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.delete: {
                if (selectedID >= 0 && selectedID < lines.size()) {
                    openDeleteDialog(v);
                }
                else {
                    Toast.makeText(this, "Choose the contact!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            String newName = extras.getString(Line.keyName);
            String newPhone = extras.getString(Line.keyPhone);
            int newImage = extras.getInt(Line.keyImage);
            switch (requestCode) {
                case addContactID: {
                    lines.add(new Line(newName, newPhone, newImage));
                    updateList();
                    selectedID = -1;
                    break;
                }
                case editContactID: {
                    lines.set(selectedID, new Line(newName, newPhone, newImage));
                    selectedID = -1;
                    updateList();
                    break;
                }
            }

        }
    }
    public void openDeleteDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete")
                .setMessage("Delete this contact?")
                .setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lines.remove(selectedID);
                updateList();
                deleteData();
                selectedID = -1;

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void deleteData() {
        Integer deletedRows = databaseHelper.deleteData(selectedID+1+"");

        if (deletedRows > 0) {
            Toast.makeText(this, "Data has been deleted", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Nothing has been deleted", Toast.LENGTH_SHORT).show();
    }
    public static int imgSearch (int img) {
        for(int i = 0; i < 8; i++) {
            if(img == icons[i])
                return i;
        }
        return -1;
    }
    public void fillList() {
        int i;
        if(lines.isEmpty()){
            lines.add(new Line("Arthur", "0663464409", R.drawable.icon_1));
            lines.add(new Line("Thomas", "0999396112", R.drawable.icon_1));
            lines.add(new Line("Finn", "0666709737", R.drawable.icon_1));
            lines.add(new Line("Polly", "0503312955", R.drawable.icon_1));
            lines.add(new Line("Michael", "0996436296", R.drawable.icon_1));
            lines.add(new Line("John", "0678731211", R.drawable.icon_1));
            lines.add(new Line("Alfie", "0662228973", R.drawable.icon_1));
            lines.add(new Line("Luca", "0683248149", R.drawable.icon_1));
            lines.add(new Line("Freddie", "0509872332", R.drawable.icon_1));
        }

        if(lines.size() == 9) {
            for(i = 0; i < lines.size(); i++) {
                databaseHelper.insertData(i,"","","","");
            }
        }

    }


}

// TO DO
// Store contacts in the database
