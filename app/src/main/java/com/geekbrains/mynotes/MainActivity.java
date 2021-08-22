package com.geekbrains.mynotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geekbrains.mynotes.autorization.GoogleAutorization;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
//import java.util.concurrent.Flow;

public class MainActivity extends AppCompatActivity {

    // элементы управления
    private TextView tvRegViewTop;
    private TextView tvRegViewBottom;
    private Button btnSignInGoogle;
    private Button btnSignInVK;
    private Button btnSignInFacebook;
    private Button btnSignInEmail;

    // авторизация Google
    private GoogleAutorization googleAutorization = new GoogleAutorization(this);
    private GoogleSignInClient googleSignInClient;

    /*// прочая старая херня
    private Navigation navigation;
    //private Publisher publisher = new Publisher();
    private FragmentList fragmentListObj;
    private ListNotesAdapter adapter;
    private Fragment fragmentList;
    private Fragment fragmentStart;
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

    public Navigation getNavigation() {
        return navigation;
    }

     */

    //public Publisher getPublisher() {
    //    return publisher;
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initElements();
        //navigation = new Navigation(getSupportFragmentManager());
        //getNavigation().addFragment(StartFragment.newInstance(), false);
        //orientation = getResources().getConfiguration().orientation;
        //addFragmentStart();
        //addFragmentList();
        //initToolbar();
    }

    private void initElements() {

        // проинициализируем переменные элементов управления
        tvRegViewTop = findViewById(R.id.textRegViewTop);
        tvRegViewBottom = findViewById(R.id.textReg_ViewBottom);
        btnSignInGoogle = findViewById(R.id.sign_in_google);
        btnSignInVK = findViewById(R.id.sign_in_vk);
        btnSignInFacebook = findViewById(R.id.sign_in_facebook);
        btnSignInEmail = findViewById(R.id.sign_in_email);

        // навесим обработчики событий
        btnSignInGoogle.setOnClickListener(view -> {
            googleAutorization.initGoogleSign();
            // получим сам клиент
            //googleSignInClient = googleAutorization.getGoogleSignInClient();
           // Intent intent = googleAutorization.signIn(googleSignInClient);
           // startActivityForResult(intent,googleAutorization.getRcSignIn());
        });
        btnSignInVK.setOnClickListener(view -> {

        });
        btnSignInFacebook.setOnClickListener(view -> {

        });
        btnSignInEmail.setOnClickListener(view -> {

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == googleAutorization.getRcSignIn()) {
            // Когда сюда возвращается TASK, результаты аутентификаи уже готовы
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            googleAutorization.handleSignInResult(task);
        }
    }

    /*@Override
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

     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // пока закомментируем весь когд в данном обработчике
        // так как пока непонятно, как вызывать его повторно

       /* getMenuInflater().inflate(R.menu.menu_drawer, menu);
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
                adapter.updateList();
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
        /*
    }

    /*private void setThemeUsers(boolean isChecked) {
        if (isChecked){
            setTheme(R.style.AppTheme_AppBarOverlay);
        }else {
            setTheme(R.style.AppTheme);
        } */
    }

   /* @Override
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

    private void addFragmentStart() {
        fragmentStart = new StartFragmentAuth();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.fragment_start,fragmentStart)
                .addToBackStack(null);
        fragmentTransaction.commit();


    }

    */

    /*private void addFragmentList() {
        // передадим параметры
        Bundle bundle = new Bundle();
        fragmentList = new FragmentList();
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_list_insert, fragmentList);
        //fragmentTransaction.replace((orientation == Configuration.ORIENTATION_PORTRAIT)
        //       ?R.id.fragment_notes_insert :R.id.fragment_list_insert_land, fragment);
       // fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        //fragmentTransaction.commit();
    }

    /*@Override
    public void replaceStartFragment() {
        // передадим параметры
        Bundle bundle = new Bundle();
        fragmentList = new FragmentList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .replace(R.id.fragment_list,fragmentList)
                .addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void addFragmentNotes() {
        Fragment fragment = new FragmentNotes();
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_list_insert, fragment);
        //fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        //fragmentTransaction.commit();
    }

     */

}