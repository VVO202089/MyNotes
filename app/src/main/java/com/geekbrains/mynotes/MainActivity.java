package com.geekbrains.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentList fragmentListObj;
    private ListNotesAdapter adapter;
    private Fragment fragmentList;
    private CardMyNotes myNotes;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private int orientation;
    private int count;
    //private SwitchCompat turnDarkTheme;
    private Boolean ChooseDarkTheme = false;
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //    outState.putBoolean("ChooseDarkTheme",turnDarkTheme.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // ChooseDarkTheme = savedInstanceState.getBoolean("ChooseDarkTheme");
        // установим тему
        //setThemeUsers(ChooseDarkTheme);
    }

    @Override
    public void onBackPressed() {
        toBack();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        count = getSupportFragmentManager().getBackStackEntryCount();
        // не добавляем кнопку "Назад"
        getSupportActionBar().setDisplayHomeAsUpEnabled(count != 0);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        // инициализация адаптера и списка заметок
        fragmentListObj = ((FragmentList) fragmentList);
        adapter = ((ListNotesAdapter) fragmentListObj.getAdapter());
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
       /* MenuItem switchDarkThemeItem = menu.findItem(R.id.switchDarkTheme);
        turnDarkTheme = (SwitchCompat) switchDarkThemeItem.getActionView();
        // установим текст программно (неполучилось этого сделать в XML)
        turnDarkTheme.setText("Темная тема");
        turnDarkTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ChooseDarkTheme = isChecked;
            setThemeUsers(isChecked);
            recreate();
            //finish();
            //startActivity(getIntent());
        });
        // установим выбранную тему, установленную по умолчанию
        setThemeUsers(ChooseDarkTheme);
        turnDarkTheme.setChecked(ChooseDarkTheme);*/
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    /*private void setThemeUsers(boolean isChecked) {
        if (isChecked){
            setTheme(R.style.AppTheme_AppBarOverlay);
        }else {
            setTheme(R.style.AppTheme);
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // проверяем, что нажато и делаем соответствующее действие
        adapter = ((ListNotesAdapter) fragmentListObj.getAdapter());
        switch (item.getItemId()) {
            case android.R.id.home:
                toBack();
                return true;
            case R.id.menu_add:
                addFragmentNotes();
                return true;
            case R.id.menu_clear:
                adapter.clearCardNotes();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toBack() {

        count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 2) {
            // уберем кнопку "Назад"
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        if (count == 1) {
            createQuitDialog();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void createQuitDialog() {
        AlertDialog.Builder builderDesc = new AlertDialog.Builder(this);
        builderDesc.setTitle("Действительно хотите выйти?");
        // навесим слушатели
        builderDesc.setPositiveButton(R.string.yes, (dialog, which) -> {
            // выходим из приложения
            this.finishAffinity();
        });
        builderDesc.setNegativeButton(R.string.no, (dialog, which) -> {
        });
        builderDesc.setNegativeButton(R.string.Cancel, (dialog, which) -> {
        });
        // создадим сам Alert
        AlertDialog alertDialog = builderDesc.create();
        alertDialog.show();
    }

    private void addFragmentList() {
        // передадим параметры
        Bundle bundle = new Bundle();
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
        fragmentTransaction.replace(R.id.fragment_list_insert, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

}