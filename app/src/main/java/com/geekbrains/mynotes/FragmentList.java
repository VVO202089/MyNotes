package com.geekbrains.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentList extends Fragment implements CardMyNotesFireBaseSource{

    private CardMyNotes myNotes;
    private CardMyNotesSource adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if ((ArrayList) bundle.get(CardMyNotes.class.getSimpleName()) != null) {
                arrayNotes = (ArrayList) bundle.get(CardMyNotes.class.getSimpleName());
            }
        }
        initRecyclerView(view);
        initOtherElements();
    }

    private void initRecyclerView(View v) {
        // для теста
        CardMyNotes myNotes1 = new CardMyNotes("Notes1", "Тестовая первая заметка", "01.01.2021");
        CardMyNotes myNotes2 = new CardMyNotes("Notes2", "Тестовая вторая заметка", "02.01.2021");
        CardMyNotes myNotes3 = new CardMyNotes("Notes3", "Тестовая третья заметка", "03.01.2021");
        // добавим их в массив
        arrayNotes.add(myNotes1);
        arrayNotes.add(myNotes2);
        arrayNotes.add(myNotes3);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_notes_line);
        adapter = new ListNotesAdapter(getContext(), arrayNotes,getActivity().getResources().getConfiguration().orientation);

        recyclerView.setAdapter((ListNotesAdapter) adapter);

    }

    private void initOtherElements() {

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

    @Override
    public void initFireBase(CardMyNotesResponse myNotesResponse) {
        if (myNotesResponse != null){
            myNotesResponse.initialized(this);
        }
    }
}
