package com.geekbrains.mynotes;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> implements CardMyNotesSource {

    private LayoutInflater inflater;
    private List<CardMyNotes> ListNotes;
    private CardMyNotesSource dataSource;
    private int orientation;

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
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
            openFragmentNotes(position);
        }
    }

    @Override
    public void deleteCardNotes(int position) {
        ListNotes.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void updateCardNotes(int position, CardMyNotes cardNotes) {

    }

    @Override
    public void addCardNotes(CardMyNotes cardNotes) {
        ListNotes.add(cardNotes);
    }

    @Override
    public void clearCardNotes() {
        ListNotes.clear();
    }

    //@Override
    //public void initialized(CardMyNotes myNotes) {
//
   // }

    // конструкто класса
    public ListNotesAdapter(Context context, List<CardMyNotes> ListNotes,int orientation) {
        this.inflater = LayoutInflater.from(context);
        this.ListNotes = ListNotes;
        this.orientation = orientation;
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
        arguments.putSerializable(CardMyNotes.class.getSimpleName(),ListNotes.get(position));
        frNotes.setArguments(arguments);
        FragmentManager fragmentManager = ((AppCompatActivity)inflater.getContext()).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_notes_insert,frNotes)
                .addToBackStack(null)
                .commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView_notes;

        ViewHolder(View view) {
            super(view);
            view.setOnCreateContextMenuListener(this);
            textView_notes = (TextView) view.findViewById(R.id.textView_notes_list);
            textView_notes.setOnClickListener(v -> {
                //if (listener != null) {
                    // инициализируем меню
                    PopupMenu menu = new PopupMenu(v.getContext(), view);
                    menu.inflate(R.menu.cards_menu);
                    menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            return false;
                        }
                    });
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
                //}
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.action_open,Menu.NONE, R.string.Open);
            menu.add(Menu.NONE, R.id.action_del, Menu.NONE, R.string.Del);
        }

    }
}
