package com.geekbrains.mynotes;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class FragmentNotes extends Fragment {

    private ActionBar toolbar;
    private View rootView;
    private Toolbar mToolbar;
    private Context context;
    private ArrayList<CardMyNotes> arrayNotes;
    private int positionNotes;
    private CardMyNotes myNotes;
    private TextView editTextTextPersonName;
    private DatePicker mDatePicker;
    private TextView editDescNotes;
    private Date dateNotes;
    private Button saveButton;
    private static final String NOTES_COLLECTION = "NOTES";
    // база данных FireStore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    // коллекция документов
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        toolbar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // для открываем карточку заметки для просмотра
        Bundle bundle = getArguments();
        if (bundle != null) {
            myNotes = (CardMyNotes) bundle.getSerializable(CardMyNotes.class.getSimpleName());
            positionNotes = bundle.getInt("position");
        }

        initElements(view);
    }

    private void initElements(View v) {
        context = getContext();
        // инициализируем EditText
        editTextTextPersonName = (TextView) v.findViewById(R.id.editTextTextPersonName);
        mDatePicker = (DatePicker) v.findViewById(R.id.datePicker);
        editDescNotes = (TextView) v.findViewById(R.id.editDescNotes);
        saveButton = (Button)v.findViewById(R.id.saveButton);
        if (myNotes == null) {
            dateNotes = new Date();
        } else {
            dateNotes = myNotes.getDateCreate();
        }
        Calendar mDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        mDate.setTime(dateNotes);
        mDatePicker.setEnabled(true);
        mDatePicker.init(mDate.get(Calendar.YEAR),
                mDate.get(Calendar.MONTH),
                mDate.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // если дата изменилась, то обновим значение
                        //dateNotes.setYear(year);
                        //dateNotes.setMonth(monthOfYear + 1);
                       // dateNotes.setDate(dayOfMonth);
                    }
                });
        if (myNotes != null) {
            editTextTextPersonName.setText(myNotes.getName());
            editDescNotes.setText(myNotes.getDescription());
        }
        // установим слушатель для ввода наименования заметки
        editTextTextPersonName.setOnClickListener(v14 -> {
            createPersonName();
        });
        // установим слушатель на editDescNotes
        editDescNotes.setOnClickListener(v13 -> {
            // создаем новый Alert для ввода
            createInputDescNotes();
        });
        // сохраняем данные
        saveButton.setOnClickListener(v1 -> {
            // проверим заполнение полей
            if (!checkMyNotes()){
                // если карточка новая, тогда добавляем в список
                if (myNotes == null) {
                    // после этого добавляем заметку в базу данных
                    myNotes = new CardMyNotes(
                            String.valueOf(editTextTextPersonName.getText()),
                            String.valueOf(editDescNotes.getText()),
                            dateNotes);
                    addNotes();
                } else {
                    //обновим поля карточки заметки
                    myNotes.setName(String.valueOf(editTextTextPersonName.getText()));
                    myNotes.setDateCreate(dateNotes);
                    myNotes.setDescription(String.valueOf(editDescNotes.getText()));
                    updateCardNotes(positionNotes);
                }
            }
        });
    }

    private boolean checkMyNotes() {

        boolean error = false;
        error = String.valueOf(editTextTextPersonName.getText()).equals("");
        if (error){
            Toast.makeText(context, "Не указано наименование", Toast.LENGTH_SHORT).show();
            return error;
        }
        error = String.valueOf(editDescNotes.getText()).equals("");
        if (error){
            Toast.makeText(context, "Не указано описание", Toast.LENGTH_SHORT).show();
            return error;
        }
        return error;
    }

    private void createPersonName() {
        AlertDialog.Builder builderDesc = new AlertDialog.Builder(context);
        builderDesc.setTitle("Введите заголовок");
        // строка для ввода
        final EditText input = new EditText(context);
        input.setText(editTextTextPersonName.getText());
        input.setSingleLine(true);
        input.setGravity(Gravity.LEFT | Gravity.TOP);
        // добавим строку для ввода описания
        builderDesc.setView(input);
        // навесим слушатели
        builderDesc.setPositiveButton(R.string.SaveNotes, (dialog, which) -> {
            editTextTextPersonName.setText(input.getText());
        });
        builderDesc.setNegativeButton(R.string.Cancel, (dialog, which) -> {
        });
        // создадим сам Alert
        AlertDialog alertDialog = builderDesc.create();
        alertDialog.show();
    }

    private void createInputDescNotes() {

        AlertDialog.Builder builderDesc = new AlertDialog.Builder(context);
        builderDesc.setTitle("Введите описание заметки");
        // строка для ввода
        final EditText input = new EditText(context);
        input.setText(editDescNotes.getText());
        input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setSingleLine(false);
        input.setGravity(Gravity.LEFT | Gravity.TOP);
        // добавим строку для ввода описания
        builderDesc.setView(input);
        // навесим слушатели
        builderDesc.setPositiveButton(R.string.SaveNotes, (dialog, which) -> {
            editDescNotes.setText(input.getText());
        });
        builderDesc.setNegativeButton(R.string.Cancel, (dialog, which) -> {
        });
        // создадим сам Alert
        AlertDialog alertDialog = builderDesc.create();
        alertDialog.show();

    }

    private void addNotes() {
        collection.add(CardMyNotesMapping.toDocument(myNotes)).addOnSuccessListener(
                documentReference -> myNotes.setId(documentReference.getId()));
    }

    public void updateCardNotes(int position) {
        String id = myNotes.getId();
        collection.document(id).set(CardMyNotesMapping.toDocument(myNotes));
    }

}
