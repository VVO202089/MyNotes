package com.geekbrains.mynotes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FragmentList extends Fragment {

    private LayoutInflater Myinflater;
    private CardMyNotes myNotes;
    private CardMyNotesSource myNotesSource;
    private ListNotesAdapter adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private FragmentActivity myContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Myinflater = inflater;
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        layoutManager = new LinearLayoutManager(
                getContext()
               ,(orientation == 1) ? LinearLayoutManager.VERTICAL:LinearLayoutManager.HORIZONTAL
               ,false);
        initRecyclerView(view);
        CardMyNotes myNotes;
        Bundle bundle = getArguments();
        if (bundle != null) {
            myNotes = (CardMyNotes) bundle.getSerializable(CardMyNotes.class.getSimpleName());
            //arrayNotes.addCardNotes(myNotes);
        }
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

        recyclerView = view.findViewById(R.id.recycler_notes_line);
        recyclerView.setLayoutManager(layoutManager);
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
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_notes_insert,notesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_drawer,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // проверяем, что нажато и делаем соответствующее действие
        switch (item.getItemId()) {
            case R.id.menu_add:
                FragmentNotes notesFragment = new FragmentNotes();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_notes_insert,notesFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.menu_clear:
                arrayNotes.clear();
                adapter.notifyDataSetChanged();
                break;
            default:
                System.out.println("true");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
