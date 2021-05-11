package com.geekbrains.mynotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Card_MyNotes> arrayNotes = new ArrayList<>();
    private EditText editTextTextPersonName;
    private EditText editTextDate;
    private EditText editDescNotes;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotesFragment() {
        super(R.layout.fragment_notes);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initElements(view);
    }

    private void initElements(View view) {
        // определим элементы управления и обработчики
        LinearLayout layout = (LinearLayout)view;
        editTextTextPersonName = (EditText)view.findViewById(R.id.editTextTextPersonName);
        editTextDate = (EditText)view.findViewById(R.id.editTextDate);
        editDescNotes = (EditText)view.findViewById(R.id.editDescNotes);
        // выводим список созданных заметок
        for (Card_MyNotes notes: arrayNotes) {
            String note = notes.getName();
            TextView ViewNote = new TextView(getContext());
            ViewNote.setText(note);
            ViewNote.setTextSize(30);
            layout.addView(ViewNote);
            // добавим слушателя
            ViewNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // выведем на экран содержание заметки
                    //showNote(notes);
                }
            });

        }

        Button AddNotes = (Button)view.findViewById(R.id.SaveNotes);
        AddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card_MyNotes myNotes = new Card_MyNotes(
                        String.valueOf(editTextTextPersonName.getText()),
                        String.valueOf(editDescNotes.getText()),
                        String.valueOf(String.valueOf(editTextDate.getText()))
                );
                //
                arrayNotes.add(myNotes);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
        /*Context context = getActivity().getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(Color.BLUE);
        TextView text = new TextView(context);
        text.setText("Это область фрагмента");
        layout.addView(text);

        return layout;*/

    }

    public ArrayList<Card_MyNotes> getArrayNotes() {
        return arrayNotes;
    }
}