package com.geekbrains.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class FragmentNotes extends Fragment {

    private ArrayList<CardMyNotes> arrayNotes;
    private int positionNotes;
    private CardMyNotes myNotes;
    private EditText editTextTextPersonName;
    private DatePicker mDatePicker;
    private EditText editDescNotes;
    private Button saveButton;
    private Button goListNotesButton;
    private Date dateNotes;
    private static final String NOTES_COLLECTION = "NOTES";
    //private final String BASE_URL = "https://mynotes-d6047-default-rtdb.firebaseio.com/";
    // база данных FireStore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    // коллекция документов
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
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
        // инициализируем EditText
        editTextTextPersonName = (EditText) v.findViewById(R.id.editTextTextPersonName);
        mDatePicker = (DatePicker) v.findViewById(R.id.datePicker);
        editDescNotes = (EditText) v.findViewById(R.id.editDescNotes);
        // поле даты сделаем недоступным для релактирования
        // инициализируем текущую дату, у нас происходит создание новой заметки
        if (myNotes == null) {
            dateNotes = new Date();
        } else {
            dateNotes = myNotes.getDateCreate();
        }
        Calendar mDate = Calendar.getInstance();
        mDate.setTime(dateNotes);
        mDatePicker.init(mDate.get(Calendar.YEAR),
                mDate.get(Calendar.MONTH),
                mDate.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // если дата изменилась, то обновим значение
                        dateNotes.setYear(year);
                        dateNotes.setMonth(monthOfYear + 1);
                        dateNotes.setDate(dayOfMonth);
                    }
                });
        //инициализация кнопок
        saveButton = (Button) v.findViewById(R.id.SaveNotes);
        goListNotesButton = (Button) v.findViewById(R.id.goListNotes);
        // если передали параметр, то заполним данные
        if (myNotes != null) {
            editTextTextPersonName.setText(myNotes.getName());
            editDescNotes.setText(myNotes.getDescription());
        }
        // настроим слушатели для кнопок
        saveButton.setOnClickListener(v1 -> {
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
        });

        goListNotesButton.setOnClickListener(v12 -> {
            openFragmentList();
        });

    }

    private void openFragmentList() {
        Fragment fragment = new FragmentList();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_list_insert, fragment)
                .addToBackStack(null)
                .commit();
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
