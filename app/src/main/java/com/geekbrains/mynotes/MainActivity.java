package com.geekbrains.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Fragment fragmentList;
    private CardMyNotes myNotes;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private ListNotesAdapter adapter;
    private int orientation;
    private SwitchCompat turnDarkTheme;
    private static final String NOTES_COLLECTION = "NOTES";
    // база данных FireStore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    // коллекция документов
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // реализуем поле поиска
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchText.clearFocus();
                //Toast.makeText(MainActivity.this,query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FragmentList fragmentListObj = ((FragmentList) fragmentList);
                adapter = ((ListNotesAdapter) fragmentListObj.getAdapter());
                // преобразуем в нижний регистр
                newText = newText.toLowerCase();
                ArrayList<CardMyNotes> newList = new ArrayList<>();
                // идем по всему изначальному массиву
                for (CardMyNotes myNotes : fragmentListObj.getArrayNotes()) {
                    String name = myNotes.getName().toLowerCase();
                    if (name.contains(newText)) {
                        newList.add(myNotes);
                    }
                }
                adapter.setFilter(newList);
                return true;
            }
        });
        // Определим переключатель для смены темы
        MenuItem switchDarkThemeItem = menu.findItem(R.id.switchDarkTheme);
        turnDarkTheme = (SwitchCompat) switchDarkThemeItem.getActionView();
        // установим текст программно (неполучилось этого сделать в XML)
        turnDarkTheme.setText("Темная тема");
        turnDarkTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setThemeUsers(isChecked);
            recreate();
        });
        // установим выбранную тему, установленную по умолчанию
        setThemeUsers(turnDarkTheme.isChecked());
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    private void setThemeUsers(boolean isChecked) {
        if (isChecked){
            setTheme(R.style.AppTheme_AppBarOverlay);
        }else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // проверяем, что нажато и делаем соответствующее действие
        switch (item.getItemId()) {
            case R.id.menu_add:
                addFragmentNotes();
                break;
            case R.id.menu_clear:
                adapter.clearCardNotes();
                //arrayNotes.clear();
                adapter.notifyDataSetChanged();
                break;
            default:
                System.out.println("true");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFragmentList() {
        fragmentList = new FragmentList();
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_list_insert, fragmentList);
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
        //fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

}