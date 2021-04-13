package com.geekbrains.mynotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Fragment fragment = new Fragment();
        addFragment(fragment);
        setContentView(R.layout.activity_main);
        //initElements();
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