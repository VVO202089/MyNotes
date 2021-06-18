package com.geekbrains.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CardMyNotes myNotes;
    private ListNotesAdapter adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private int orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // уже не нужно, так как данные хранятся в FireBase
        /*Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            arrayNotes.add((CardMyNotes) arguments.getSerializable(CardMyNotes.class.getSimpleName()));
        }*/
        setContentView(R.layout.activity_main);
        orientation = getResources().getConfiguration().orientation;
        addFragmentList();
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // проверяем, что нажато и делаем соответствующее действие
        switch (item.getItemId()) {
            case R.id.menu_add:
                addFragmentNotes();
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

    private void addFragmentList() {
        Fragment fragment = new FragmentList();
        // уже не нужно, так как данные хранятся в Firebase
        /*Bundle bundle = new Bundle();
        if (arrayNotes != null) {
            bundle.putSerializable("arrayNotes", arrayNotes);
        }
        fragment.setArguments(bundle);*/
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_list_insert,fragment);
        //fragmentTransaction.replace((orientation == Configuration.ORIENTATION_PORTRAIT)
        //       ?R.id.fragment_notes_insert :R.id.fragment_list_insert_land, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

    private void addFragmentNotes() {
        Fragment fragment = new FragmentNotes();
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_notes_insert, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

}