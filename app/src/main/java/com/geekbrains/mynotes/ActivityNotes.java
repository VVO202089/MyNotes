package com.geekbrains.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentHostCallback;
import androidx.fragment.app.FragmentTransaction;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.sql.Date;

public class ActivityNotes extends AppCompatActivity {

    private final static String keyNotes = "Notes";
    private MyNotes myNotes;

    private EditText editTextTextPersonName;
    private EditText editTextDate;
    private EditText editDescNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        // закрываем активити, если экран в ландшафтной ориентации
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        initElements();
        fillingData();
    }

    private void fillingData() {
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            myNotes = (MyNotes) arguments.getSerializable(MyNotes.class.getSimpleName());
            if (myNotes != null) {
                editTextTextPersonName.setText(myNotes.getName());
                editTextDate.setText(myNotes.getDateCreate());
                editDescNotes.setText(myNotes.getDescription());
            }
        }
    }

    private void initElements() {
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextDate = findViewById(R.id.editTextDate);
        editDescNotes = findViewById(R.id.editDescNotes);
        Button addNotes = findViewById(R.id.addNotes);
        // при нажатии на кнопку - добавляем заметку
        addNotes.setOnClickListener(v -> {
            MyNotes myNotes = new MyNotes(
                    String.valueOf(editTextTextPersonName.getText()),
                    String.valueOf(editDescNotes.getText()),
                    String.valueOf(String.valueOf(editTextDate.getText())));
            goToMain(myNotes);
        });
        Button goListNotes = findViewById(R.id.goListNotes);
        // при нажатии на кнопку возврат к списку заметок
        goListNotes.setOnClickListener(v -> goToListNotes());

    }

    private void goToListNotes() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void goToMain(MyNotes myNotes) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MyNotes.class.getSimpleName(), myNotes);
        startActivity(intent);
    }

}