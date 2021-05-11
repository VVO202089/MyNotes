package com.geekbrains.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ActivityNotes extends AppCompatActivity {

    private final static String keyNotes = "Notes";
    private Card_MyNotes myNotes;

    private boolean checkAdd;
    private EditText editTextTextPersonName;
    private EditText editTextDate;
    private EditText editDescNotes;
    private Button saveOrAddNotesButton;

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
            myNotes = (Card_MyNotes) arguments.getSerializable(Card_MyNotes.class.getSimpleName());
            if (myNotes != null) {
                editTextTextPersonName.setText(myNotes.getName());
                editTextDate.setText(myNotes.getDateCreate());
                editDescNotes.setText(myNotes.getDescription());
                saveOrAddNotesButton.setText("Сохранить");
            }
        } else {
            checkAdd = true;
            saveOrAddNotesButton.setText("Добавить заметку");
        }
    }

    private void initElements() {
        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        editTextDate = findViewById(R.id.editTextDate);
        editDescNotes = findViewById(R.id.editDescNotes);
        saveOrAddNotesButton = findViewById(R.id.SaveNotes);
        //Button saveOrAddNotesButton = findViewById(R.id.SaveNotes);
        // при нажатии на кнопку - или добавляем заметку или сохраняем изменения
        saveOrAddNotesButton.setOnClickListener(v -> {
            if (checkAdd) {
                Card_MyNotes myNotes = new Card_MyNotes(
                        String.valueOf(editTextTextPersonName.getText()),
                        String.valueOf(editDescNotes.getText()),
                        String.valueOf(String.valueOf(editTextDate.getText())));
                goToMain(myNotes);
            } else {

            }
        });
        Button goListNotes = findViewById(R.id.goListNotes);
        // при нажатии на кнопку возврат к списку заметок
        goListNotes.setOnClickListener(v -> goToListNotes());

    }

    private void goToListNotes() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToMain(Card_MyNotes myNotes) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Card_MyNotes.class.getSimpleName(), myNotes);
        startActivity(intent);
    }

}