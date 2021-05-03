package com.geekbrains.mynotes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<MyNotes> ListNotes;
    //private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // конструкто класса
    public ListNotesAdapter(Context context, List<MyNotes> ListNotes) {
        this.inflater = LayoutInflater.from(context);
        this.ListNotes = ListNotes;
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
        MyNotes myNotes = ListNotes.get(position);
        holder.textView_notes.setText(myNotes.getName());
        /*holder.textView_notes.setOnClickListener(v -> {
            context = v.getContext();
            Intent intent = new Intent(context, ActivityNotes.class);
            intent.putExtra(MyNotes.class.getSimpleName(),myNotes);
            context.startActivity(intent);
        });*/
        //holder.bind(myNotes,listener);
        //holder.TextPersonNameView.setText(myNotes.getName());
        //holder.TextDateView.setText(myNotes.getDateCreate());
        //holder.editDescNotes.setText(myNotes.getDescription());

    }

    @Override
    public int getItemCount() {
        return ListNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_notes;
        //final EditText TextPersonNameView;
        //TextDateView,editDescNotes;

        ViewHolder(View view) {
            super(view);
            textView_notes = (TextView) view.findViewById(R.id.textView_notes_list);
            textView_notes.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(v, getAdapterPosition());
                }
            });
            // TextPersonNameView = (EditText)view.findViewById(R.id.editTextTextPersonName);
            //TextDateView = (EditText)view.findViewById(R.id.editTextDate);
            //editDescNotes = (EditText)view.findViewById(R.id.editDescNotes);
        }
    }
}
