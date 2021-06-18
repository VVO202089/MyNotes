package com.geekbrains.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class FragmentNotes extends Fragment {

    private ArrayList<CardMyNotes> arrayNotes;
    private CardMyNotes myNotes;
    private EditText editTextTextPersonName;
    private EditText editTextDate;
    private EditText editDescNotes;
    private Button saveButton;
    private Button goListNotesButton;
    private DatabaseReference mDataBase;
    private String NOTES_KEY = "NOTES";
    private final String BASE_URL = "https://mynotes-d6047-default-rtdb.firebaseio.com/";

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
        }

        initElements(view);
    }

    private void initElements(View v) {
        // инициализируем mDataBase
        mDataBase = FirebaseDatabase.getInstance(BASE_URL).getReference(NOTES_KEY);
        // инициализируем EditText
        editTextTextPersonName = (EditText) v.findViewById(R.id.editTextTextPersonName);
        editTextDate = (EditText) v.findViewById(R.id.editTextDate);
        editDescNotes = (EditText) v.findViewById(R.id.editDescNotes);
        //инициализация кнопок
        saveButton = (Button) v.findViewById(R.id.SaveNotes);
        goListNotesButton = (Button) v.findViewById(R.id.goListNotes);
        // если передали параметр, то заполним данные
        if (myNotes != null) {
            CardMyNotes answer;
            answer = new CardMyNotes(myNotes.getId(),String.valueOf(editTextTextPersonName.getText())
                    ,String.valueOf(editDescNotes.getText()),String.valueOf(editTextDate.getText()));
        }
        if (myNotes != null) {
            editTextTextPersonName.setText(myNotes.getName());
            editTextDate.setText(myNotes.getDateCreate());
            editDescNotes.setText(myNotes.getDescription());
        }
        // настроим слушатели для кнопок
        saveButton.setOnClickListener(v1 -> {
            // если карточка новая, тогда добавляем в список
            if (myNotes == null) {
                Random random = new Random();
                myNotes = new CardMyNotes(String.valueOf(random.nextInt(100000000))
                        ,String.valueOf(editTextTextPersonName.getText())
                        , String.valueOf(editTextDate.getText())
                        , String.valueOf(editDescNotes.getText()));
                // добавляем экемпляр класса в список (не требуется)
                //arrayNotes.add(myNotes);
                // после этого добавляем заметку в базу данных
                addFireBase();
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
        fragmentTransaction.replace(R.id.fragment_list_insert,fragment)
                            .addToBackStack(null)
                            .commit();
    }

    private void addFireBase() {
        mDataBase.push().setValue(myNotes);
    }

    /*private void addFragmentList() {
        Fragment fragment = new FragmentList();
        Bundle bundle = new Bundle();
        if (arrayNotes != null) {
            bundle.putSerializable("arrayNotes", arrayNotes);
        }
        fragment.setArguments(bundle);
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getChildFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_list_insert, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }*/
    
}
