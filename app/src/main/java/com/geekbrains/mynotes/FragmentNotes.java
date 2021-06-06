package com.geekbrains.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentNotes extends Fragment {

    private CardMyNotes myNotes;
    private EditText editTextTextPersonName;
    private EditText editTextDate;
    private EditText editDescNotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (bundle!= null){
            myNotes = (CardMyNotes)bundle.getSerializable(CardMyNotes.class.getSimpleName());
        }

        initElements(view);
    }

    private void initElements(View v) {
        // инициализируем EditText
        editTextTextPersonName = (EditText)v.findViewById(R.id.editTextTextPersonName);
        editTextDate = (EditText)v.findViewById(R.id.editTextDate);
        editDescNotes = (EditText)v.findViewById(R.id.editDescNotes);
        // если передали параметр, то заполним данные
        if(myNotes!=null){
            editTextTextPersonName.setText(myNotes.getName());
            editTextDate.setText(myNotes.getDateCreate());
            editDescNotes.setText(myNotes.getDescription());
        }

    }
}
