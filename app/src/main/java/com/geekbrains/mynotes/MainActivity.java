package com.geekbrains.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CardMyNotes myNotes;
    private ListNotesAdapter adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private final String RezKey = "RezKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bundle arguments = getIntent().getExtras();
        //if (arguments != null) {
        //    arrayNotes.add((CardMyNotes) arguments.getSerializable(CardMyNotes.class.getSimpleName()));
        //}
        setContentView(R.layout.activity_main);
        //if (savedInstanceState == null) {
        addFragmentList();
       // }
        initView();
        //initRecyclerView();
        //initToolbar();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
       // outState.put
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    /*private void initRecyclerView() {
        // для теста
        CardMyNotes myNotes1 = new CardMyNotes("Notes1", "Тестовая первая заметка", "01.01.2021");
        CardMyNotes myNotes2 = new CardMyNotes("Notes2", "Тестовая вторая заметка", "02.01.2021");
        CardMyNotes myNotes3 = new CardMyNotes("Notes3", "Тестовая третья заметка", "03.01.2021");
        // добавим их в массив
        arrayNotes.add(myNotes1);
        arrayNotes.add(myNotes2);
        arrayNotes.add(myNotes3);

        RecyclerView recyclerView = findViewById(R.id.recycler_notes_line);
        adapter = new ListNotesAdapter(this, arrayNotes);
        recyclerView.setAdapter(adapter);
        adapter.setListener((view, position) -> {
            if (arrayNotes.get(position) != null) {
                // не используем активити для отображения данных
                //Intent intent = new Intent(getBaseContext(), ActivityNotes.class);
                //intent.putExtra(CardMyNotes.class.getSimpleName(), arrayNotes.get(position));
                //startActivity(intent);
                // используем для этих целей фрагменты
                //openNotesFragment(position);

            }
        });
        adapter.setdelListener(new ListNotesAdapter.delNotes() {
            @Override
            public void del(View view, int position) {
                arrayNotes.remove(arrayNotes.get(position));
                adapter.notifyDataSetChanged();
            }
        });
    }*/

    /*private void openNotesFragment(int position) {

        FragmentNotes fragmentNotes = new FragmentNotes();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FragmentNotes.class.getSimpleName(),arrayNotes.get(position));
        fragmentNotes.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_notes_insert,fragmentNotes)
                .addToBackStack("ActivityMain").commit();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // проверяем, что нажато и делаем соответствующее действие
        switch (item.getItemId()) {
            case R.id.menu_add:
                // отказываемся от запуска нового активити
                // работаем только с фрагментами
                //Intent intent = new Intent(getBaseContext(), ActivityNotes.class);
                //startActivity(intent);
                // добавляем фрагмент с данными заметки
                //setFragmentNotesInsert();
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

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public ArrayList<CardMyNotes> getArrayNotes() {
        return arrayNotes;
    }

    private void addFragmentList() {
        // заменяем fragment_notes_insert на наш фрагмент
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_list_insert, new FragmentList())
                .commit();
    }

}