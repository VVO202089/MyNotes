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

import java.util.ArrayList;

public class FragmentNotes extends Fragment {

    private ArrayList<CardMyNotes> arrayNotes;
    private CardMyNotes myNotes;
    private EditText editTextTextPersonName;
    private EditText editTextDate;
    private EditText editDescNotes;
    private Button saveButton;
    private Button goListNotesButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            myNotes = (CardMyNotes) bundle.getSerializable(CardMyNotes.class.getSimpleName());
        }

        initElements(view);
    }

    private void initElements(View v) {
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
            answer = new CardMyNotes(String.valueOf(editTextTextPersonName.getText())
                    ,String.valueOf(editDescNotes.getText()),String.valueOf(editTextDate.getText()));
            answer.setId(myNotes.getId());
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
                myNotes = new CardMyNotes(String.valueOf(editTextTextPersonName.getText())
                        , String.valueOf(editTextDate.getText())
                        , String.valueOf(editDescNotes.getText()));
                // добавляем экемпляр класса в список
                arrayNotes.add(myNotes);
                // открываем фрагмент со списком заметок

            }

        });

        goListNotesButton.setOnClickListener(v12 -> {

        });

    }

    private void addFragmentList() {
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
    }
}
