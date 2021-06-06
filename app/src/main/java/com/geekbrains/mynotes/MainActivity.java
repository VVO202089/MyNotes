package com.geekbrains.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CardMyNotes myNotes;
    private ListNotesAdapter adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            arrayNotes.add((CardMyNotes) arguments.getSerializable(CardMyNotes.class.getSimpleName()));
        }
        setContentView(R.layout.activity_main);
        Fragment fragmentList = new FragmentList();
        addFragment(fragmentList);
        //initView();
        // initRecyclerView();
        initToolbar();
    }

    /*private void initRecyclerView() {
        // для теста
        /*Card_MyNotes myNotes1 = new Card_MyNotes("Notes1", "Тестовая первая заметка", "01.01.2021");
        Card_MyNotes myNotes2 = new Card_MyNotes("Notes2", "Тестовая вторая заметка", "02.01.2021");
        Card_MyNotes myNotes3 = new Card_MyNotes("Notes3", "Тестовая третья заметка", "03.01.2021");
        // добавим их в массив
        arrayNotes.add(myNotes1);
        arrayNotes.add(myNotes2);
        arrayNotes.add(myNotes3);

        RecyclerView recyclerView = findViewById(R.id.recycler_notes_line);
        adapter = new ListNotesAdapter(this, arrayNotes);
        recyclerView.setAdapter(adapter);
        adapter.setListener((view, position) -> {
            if (arrayNotes.get(position) != null) {
                Intent intent = new Intent(getBaseContext(), ActivityNotes.class);
                intent.putExtra(Card_MyNotes.class.getSimpleName(), arrayNotes.get(position));
                startActivity(intent);
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
                Intent intent = new Intent(getBaseContext(), ActivityNotes.class);
                startActivity(intent);
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
    }*/

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // проверяем, что нажато и делаем соответствующее действие
        switch (item.getItemId()) {
            case R.id.menu_add:

                break;
            case R.id.menu_clear:
                arrayNotes.clear();
                adapter.notifyDataSetChanged();
                break;
            default:
                System.out.println("true");
                break;
        }
        return true;
    }

    /*public ArrayList<Card_MyNotes> getArrayNotes() {
        return arrayNotes;
    }*/

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
        fragmentTransaction.replace(R.id.fragment_list_insert, fragment);
        fragmentTransaction.addToBackStack(null);
        // Закрыть транзакцию
        fragmentTransaction.commit();
    }

}