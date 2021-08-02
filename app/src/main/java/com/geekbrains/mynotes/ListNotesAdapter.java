package com.geekbrains.mynotes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> implements CardMyNotesSource {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<CardMyNotes> ListNotes;
    private CardMyNotesSource dataSource;
    private int orientation;
    //private Button btnAlertDeleteNotes;
    private static final String NOTES_COLLECTION = "NOTES";
    // база данных FireStore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    // коллекция документов
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    private int position;

    // сеттер и геттер для position
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public CardMyNotes getCardNotes(int position) {
        return ListNotes.get(position);
    }

    @Override
    public int size() {
        return ListNotes.size();
    }

    @Override
    public void openCard(int position) {
        // откроем фрагмент с заполненными данными
        openFragmentNotes(position);
    }

    @Override
    public void deleteCardNotes(int position) {
        // создаем диалог перед удалением заметки
        createDeleteNotesDialog();
    }

    private void createDeleteNotesDialog() {
        // создаем билдер
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // укажем заголовок окна
        builder.setTitle(R.string.AlertDeleteNotes)
                .setCancelable(false)
                .setNegativeButton(R.string.no,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(context, "Нет!", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        })
                .setNeutralButton(R.string.dunno,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(context, "Не знаю!", Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                            }
                        })
                .setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(context, "Да", Toast.LENGTH_SHORT).show();
                                collection.document(ListNotes.get(position).getId()).delete();
                                ListNotes.remove(position);
                                notifyDataSetChanged();
                            }
                        });
        // покажем его
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /*private View.OnClickListener clickAlertDeleteNotes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // создаем билдер
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // укажем заголовок окна
            builder.setTitle(R.string.AlertDeleteNotes)
                    .setCancelable(false)
                    .setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "Нет!", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setNeutralButton(R.string.dunno,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "Не знаю!", Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setPositiveButton(R.string.yes,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "Да", Toast.LENGTH_SHORT).show();
                                }
                            });
            // покажем его
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    };*/

    @Override
    public void clearCardNotes() {
        for (CardMyNotes myNotes : ListNotes) {
            collection.document(myNotes.getId()).delete();
        }
        ListNotes = new ArrayList<CardMyNotes>();
    }

    @Override
    public void updateList() {
        // по - другому хз как
        notifyDataSetChanged();
    }

    // конструктор класса
    public ListNotesAdapter(Context context,
                            ArrayList<CardMyNotes> ListNotes,
                            int orientation,
                            CollectionReference collection) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.ListNotes = ListNotes;
        this.orientation = orientation;
        this.collection = collection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // данные по заметке
        View view = inflater.inflate(R.layout.item_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // привязка холдера и заметки по определенной позиции
        CardMyNotes myNotes = ListNotes.get(position);
        holder.textView_notes.setText(myNotes.getName());
    }

    @Override
    public int getItemCount() {
        return ListNotes.size();
    }

    private void openFragmentNotes(int position) {

        Fragment frNotes = new FragmentNotes();
        Bundle arguments = new Bundle();
        arguments.putSerializable(CardMyNotes.class.getSimpleName(), ListNotes.get(position));
        // так же передадим позицию заметки в списке
        arguments.putInt("position", position);
        frNotes.setArguments(arguments);
        FragmentManager fragmentManager = ((AppCompatActivity) inflater.getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_list_insert, frNotes)
                .addToBackStack(null)
                .commit();
    }

    public void setFilter(ArrayList<CardMyNotes> newList) {
        ListNotes = new ArrayList<>();
        ListNotes.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView_notes;

        ViewHolder(View view) {
            super(view);
            view.setOnCreateContextMenuListener(this);
            // пока не понятно
            textView_notes = (TextView) view.findViewById(R.id.textView_notes_list);
            textView_notes.setOnClickListener(v -> {
                // инициализируем меню
                PopupMenu menu = new PopupMenu(v.getContext(), view);
                menu.inflate(R.menu.cards_menu);
                menu.setOnMenuItemClickListener(item -> false);
                menu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.action_del:
                            deleteCardNotes(getAdapterPosition());
                            break;
                        case R.id.action_open:
                            // откроем карточку заметки
                            openCard(getAdapterPosition());
                            break;
                        default:
                            System.out.println("default");
                    }
                    return false;
                });
                menu.show();
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.action_open, Menu.NONE, R.string.Open);
            menu.add(Menu.NONE, R.id.action_del, Menu.NONE, R.string.Del);
        }

    }
}
