package com.geekbrains.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MyNotes myNotes;
    private ArrayList<MyNotes> arrayNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            arrayNotes.add((MyNotes) arguments.getSerializable(MyNotes.class.getSimpleName()));
        }
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Fragment fragment = new Fragment();
            addFragment(fragment);
        }
        setContentView(R.layout.activity_main);
        initView();
        initToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // определяем меню приложения
        getMenuInflater().inflate(R.menu.main, menu);
        // добавление элемента
        AppCompatButton addNotes = (AppCompatButton) menu.findItem(R.id.addNotes);
        MenuItem search = menu.findItem(R.id.action_search);
        // инициализируем строку поиска
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // реакция на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            // реакция на каждую клавишу
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        // Создадим слушателя для кнопки добавления заметки в верхнем меню
        /*addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("fgfdgd");
            }
        });*/

        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    private void initView() {
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public ArrayList<MyNotes> getArrayNotes() {
        return arrayNotes;
    }

    private void addFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        if (arrayNotes != null) {
            bundle.putSerializable("arrayNotes", arrayNotes);
        }
        fragment.setArguments(bundle);
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_List, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }
}