package com.geekbrains.mynotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.data.BitmapTeleporter;

import java.io.Serializable;

public class FragmentNotes extends Fragment {

    private EditText NotesPersonName;
    private EditText DataNotes;
    private EditText DescNotes;
    private Button saveButton;
    private Button goListNotesButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // связываем с фрагментом
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // ищем вьюхи во View view
        initView(view);
        CardMyNotes myNotes;
        Bundle bundle = getArguments();
        if (bundle != null) {
            myNotes = (CardMyNotes) bundle.getSerializable(CardMyNotes.class.getSimpleName());
            NotesPersonName.setText(myNotes.getName());
            DataNotes.setText(myNotes.getDateCreate());
            DescNotes.setText(myNotes.getDescription());
        }
    }

    private void initView(@NonNull View view) {
        NotesPersonName = (EditText) view.findViewById(R.id.editTextTextPersonName);
        DataNotes = (EditText)view.findViewById(R.id.editTextDate);
        DescNotes = (EditText)view.findViewById(R.id.editDescNotes);
        saveButton = view.findViewById(R.id.SaveNotes);
        goListNotesButton = view.findViewById(R.id.goListNotes);

        saveButton.setOnClickListener(v -> {
            CardMyNotes myNotes = new CardMyNotes(
                    NotesPersonName.getText().toString(),
                    DescNotes.getText().toString(),
                    DataNotes.getText().toString());
            if(myNotes != null){
                //getActivity().
            }
            closeFragment();
        });

        goListNotesButton.setOnClickListener(v -> {
            closeFragment();
            //MainActivity mainActivity  = getActivity().closeContextMenu();
            //Intent intent = new Intent(this,MainActivity.class);
           // startActivity(intent);
        });
    }

    private void closeFragment() {
        getFragmentManager().beginTransaction()
                .remove(this)
                .setTransition( getFragmentManager().beginTransaction().TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }

}
