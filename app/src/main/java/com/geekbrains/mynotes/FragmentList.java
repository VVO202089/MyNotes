package com.geekbrains.mynotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FragmentList extends Fragment {

    private CardMyNotes myNotes;
    private CardMyNotesSource adapter;
    private ArrayList<CardMyNotes> arrayNotes = new ArrayList<CardMyNotes>();
    private static final String NOTES_COLLECTION = "NOTES";

    // база данных FireStore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    // коллекция документов
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    public ArrayList<CardMyNotes> getArrayNotes() {
        return arrayNotes;
    }

    public CardMyNotesSource getAdapter() {
        return adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        //initOtherElements(view);
    }

    //private void initOtherElements(View view) {
    //    btnAlertDeleteNotes = (Button) view.findViewById(R.id.AlertDelete);
    //}

    private void initRecyclerView(View v) {
        // очищаем массив заметок
        arrayNotes.clear();

        collection.orderBy(CardMyNotesMapping.Fields.DATE_CREATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            Date dateNotes = document.getTimestamp("dateCreate").toDate();
                            CardMyNotes myNotes = CardMyNotesMapping.toCardNotes(id, doc, dateNotes);
                            if (myNotes != null) {
                                arrayNotes.add(myNotes);
                            }
                            adapter.updateList();
                        }
                    }
                });

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_notes_line);
        adapter = new ListNotesAdapter(getActivity(),
                arrayNotes,
                getActivity().getResources().getConfiguration().orientation
                , collection);
        recyclerView.setAdapter((ListNotesAdapter) adapter);
    }

    private void openFragmentNotes(int position) {
        Fragment frNotes = new FragmentNotes();
        Bundle arguments = new Bundle();
        arguments.putSerializable(CardMyNotes.class.getSimpleName(), arrayNotes.get(position));
        frNotes.setArguments(arguments);
        FragmentManager fragmentManager = getChildFragmentManager();
        //fragmentManager.beginTransaction()
        //        .replace(R.id.fragment_list_insert, frNotes)
        //        .addToBackStack(null)
        //        .commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_drawer, menu);
    }

}
