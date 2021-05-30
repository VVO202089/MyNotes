package com.geekbrains.mynotes;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<CardMyNotes> ListNotes;
    private OnItemClickListener listener;
    private delNotes delListener;

    private int position;

    // конструктор класса
    public ListNotesAdapter(LayoutInflater layoutInflater, List<CardMyNotes> ListNotes) {
        //this.inflater = LayoutInflater.from(context);
        this.inflater = layoutInflater;
        this.ListNotes = ListNotes;
    }

    // сеттер и геттер для position
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /*@Override
    public CardMyNotes getCardNotes(int position) {
        return null;
    }

    @Override
    public int size() {
        return ListNotes.size();
    }

    @Override
    public void deleteCardNotes(int position) {
        ListNotes.remove(position);
    }

    @Override
    public void updateCardNotes(int position, CardMyNotes cardNotes) {
        // тут остается вопрос
        ListNotes.set(position,cardNotes);
    }

    @Override
    public void addCardNotes(CardMyNotes cardNotes) {
        ListNotes.add(cardNotes);
    }

    @Override
    public void clearCardNotes() {
        ListNotes.clear();
    }

    @Override
    public void onItemClick(View view, int position) {

    }*/

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface delNotes{
        void del(View view, int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setdelListener(delNotes delListener) {
        this.delListener = delListener;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView_notes;

        ViewHolder(View view) {
            super(view);
            view.setOnCreateContextMenuListener(this);
            textView_notes = (TextView) view.findViewById(R.id.textView_notes_list);
            textView_notes.setOnClickListener(v -> {
                if (listener != null) {
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
                                delListener.del(v,getAdapterPosition());
                                break;
                                //ListNotes.remove(ListNotes.get(position));
                            case R.id.action_open:
                                listener.onItemClick(v, getAdapterPosition());
                                break;
                            default:
                                System.out.println("default");
                        }
                        return false;
                    });
                    menu.show();
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.action_open,Menu.NONE, R.string.Open);
            menu.add(Menu.NONE, R.id.action_del, Menu.NONE, R.string.Del);
        }

    }
}
