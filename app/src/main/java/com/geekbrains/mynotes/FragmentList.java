package com.geekbrains.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    private CardMyNotes myNotes;
    private CardMyNotesSource adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private FragmentActivity myContext;
    private DatabaseReference mDataBase;
    private String NOTES_KEY = "NOTES";
    private final String BASE_URL = "https://mynotes-d6047-default-rtdb.firebaseio.com/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // уже не нужно, так как данные хранятся в Firebase
        /*Bundle bundle = getArguments();
        if (bundle != null) {
            if ((ArrayList) bundle.get(CardMyNotes.class.getSimpleName()) != null) {
                arrayNotes = (ArrayList) bundle.get(CardMyNotes.class.getSimpleName());
            }
        }*/
        initRecyclerView(view);
        //initOtherElements();
    }

    private void initRecyclerView(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_notes_line);
        arrayNotes.clear();
        // инициализируем mDataBase
        mDataBase = FirebaseDatabase.getInstance(BASE_URL).getReference();
        // читаем данные из FireStore и заполняем arrayList
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // очищаем перед загрузкой
                if (arrayNotes.size() > 0) {
                    arrayNotes.clear();
                }

                // заполняем массив заметок
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CardMyNotes myNotes = ds.getValue(CardMyNotes.class);
                    if (myNotes != null){
                        arrayNotes.add(myNotes);
                    }
                }

                if(adapter!=null){
                   adapter.updateCardNotes(arrayNotes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        // добавление слушателя
        mDataBase.addValueEventListener(eventListener);

        // для теста (лишнее)
        CardMyNotes myNotes1 = new CardMyNotes("1","Notes1", "Тестовая первая заметка", "01.01.2021");
        CardMyNotes myNotes2 = new CardMyNotes("2","Notes2", "Тестовая вторая заметка", "02.01.2021");
        CardMyNotes myNotes3 = new CardMyNotes("3","Notes3", "Тестовая третья заметка", "03.01.2021");
        // добавим их в массив
        arrayNotes.add(myNotes1);
        arrayNotes.add(myNotes2);
        arrayNotes.add(myNotes3);

        adapter = new ListNotesAdapter(getContext(), arrayNotes, getActivity().getResources().getConfiguration().orientation);
        recyclerView.setAdapter((ListNotesAdapter) adapter);

    }

    private void initOtherElements() {

        // это сохранение, пока пусть лежит тут
        //String id = mDataBase.getKey();
        //mDataBase.push().setValue(cardNotes);

        /*myNotes = new CardMyNotesFirebase().initFireBase(new CardMyNotesResponse() {
            @Override
            public void initialized(CardMyNotesFireBaseSource myNotes) {
                // тут пока не понятно
                //adapter.notifyDataSetChanged();
            }
        });*/
        // тут пока не понятно
        //adapter.setDataSource(myNotes);

    }
    private void openFragmentNotes(int position) {
        Fragment frNotes = new FragmentNotes();
        Bundle arguments = new Bundle();
        arguments.putSerializable(CardMyNotes.class.getSimpleName(), arrayNotes.get(position));
        frNotes.setArguments(arguments);
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_list_insert, frNotes)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_drawer, menu);
    }

    /*@Override
    public void initFireBase(CardMyNotesResponse myNotesResponse) {
        if (myNotesResponse != null) {
            myNotesResponse.initialized(this);
        }
    }*/
}
