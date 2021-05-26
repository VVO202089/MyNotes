package com.geekbrains.mynotes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    private LayoutInflater Myinflater;
    private CardMyNotes myNotes;
    private ListNotesAdapter adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Myinflater = inflater;
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        // для теста
        CardMyNotes myNotes1 = new CardMyNotes("Notes1", "Тестовая первая заметка", "01.01.2021");
        CardMyNotes myNotes2 = new CardMyNotes("Notes2", "Тестовая вторая заметка", "02.01.2021");
        CardMyNotes myNotes3 = new CardMyNotes("Notes3", "Тестовая третья заметка", "03.01.2021");
        // добавим их в массив
        arrayNotes.add(myNotes1);
        arrayNotes.add(myNotes2);
        arrayNotes.add(myNotes3);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_notes_line);
        adapter = new ListNotesAdapter(Myinflater, arrayNotes);
        recyclerView.setAdapter(adapter);
        adapter.setListener((v, position) -> {
            if (arrayNotes.get(position) != null) {
                openNotesFragment(position);
            }
        });
        adapter.setdelListener(new ListNotesAdapter.delNotes() {
            @Override
            public void del(View view, int position) {
                arrayNotes.remove(arrayNotes.get(position));
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void openNotesFragment(int position) {

        Bundle arguments = new Bundle();
        arguments.putSerializable(CardMyNotes.class.getSimpleName(),arrayNotes.get(position));
        FragmentNotes notesFragment = new FragmentNotes();
        notesFragment.setArguments(arguments);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_notes_insert,notesFragment)
                .commit();
    }
}
