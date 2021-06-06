package com.geekbrains.mynotes;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    private CardMyNotes myNotes;
    private ListNotesAdapter adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
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
        adapter = new ListNotesAdapter(getContext(), arrayNotes);
        recyclerView.setAdapter(adapter);
        adapter.setListener((view, position) -> {
            if (arrayNotes.get(position) != null) {
                openFragmentNotes(position);
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

    private void openFragmentNotes(int position) {
        Fragment frNotes = new FragmentNotes();
        Bundle arguments = new Bundle();
        arguments.putSerializable(CardMyNotes.class.getSimpleName(),arrayNotes.get(position));
        frNotes.setArguments(arguments);
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_notes_insert,frNotes)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_drawer, menu);
    }

}
