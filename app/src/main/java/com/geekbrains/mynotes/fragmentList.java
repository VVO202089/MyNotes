package com.geekbrains.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private boolean isLandScape;
    //private ArrayList<MyNotes> arrayNotes = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentList.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentList newInstance(String param1, String param2) {
        fragmentList fragment = new fragmentList();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MainActivity mainActivity = (MainActivity)getActivity();
        // если ориентация главного активити горизонтальная, тогда добавляем фрагмент
        if (mainActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_List,new NotesFragment()).commit();
        }
        //arrayNotes = mainActivity.getArrayNotes();
        //if (arrayNotes!= null){
            //myNotes = (MyNotes)getArguments().getSerializable(MyNotes.class.getSimpleName());
            //arrayNotes.add(myNotes);
        //}
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initList(view);
    }

    private void initList(View view) {
        LinearLayout layout = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.Notes);
        for (String myNotes:notes) {
            createTextView(layout,myNotes);
        }
        //for (MyNotes myNotes:arrayNotes) {
        //    createTextView(layout,myNotes.getName());
       //}
        // добавим кнопку добавления заметки
        Button addNote = new Button(getContext());
        addNote.setText("Добавить заметку");
        addNote.setTextSize(30);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // открываем форму заполнения заметки
                Intent intent = new Intent();
                intent.setClass(getActivity(),ActivityNotes.class);
                startActivity(intent);
            }
        });
        layout.addView(addNote);
    }

    private void createTextView(LinearLayout layout, String datum) {
        TextView textView = new TextView(getContext());
        textView.setText(datum);
        textView.setTextSize(30);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // нужно получить нужный MyNotes
                Intent intent = new Intent(getActivity(),ActivityNotes.class);
                //intent.putExtra(ActivityNotes.class.getSimpleName(),);
                startActivity(intent);
            }
        });
        layout.addView(textView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLandScape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (isLandScape){
           //createLandFragment();
        }

    }

    private void createLandFragment() {
        //Fragment fragment = new Fragment(R.id.fragment_notes);
        NotesFragment notesFragment = NotesFragment.newInstance("get","get");
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_List, notesFragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();

    }
}