package com.geekbrains.mynotes;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class NotesSourceImpl implements CardMyNotesSource {

    private List<CardMyNotes>dataSource;

    public NotesSourceImpl(List<CardMyNotes> dataSource) {
        this.dataSource = new ArrayList<>(3);
    }

    public NotesSourceImpl init()
    {
        // пока тестовые данные
        dataSource.add(new CardMyNotes("Notes1", "Тестовая первая заметка", "01.01.2021"));
        dataSource.add(new CardMyNotes("Notes2", "Тестовая вторая заметка", "02.01.2021"));
        dataSource.add(new CardMyNotes("Notes3", "Тестовая третья заметка", "03.01.2021"));

        return this;

    }

    @Override
    public CardMyNotes getCardNotes(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void deleteCardNotes(int position) {
        dataSource.remove(position);
    }

    @Override
    public void updateCardNotes(int position, CardMyNotes cardNotes) {
        dataSource.set(position,cardNotes);
    }

    @Override
    public void addCardNotes(CardMyNotes cardNotes) {
        dataSource.add(cardNotes);
    }

    @Override
    public void clearCardNotes() {
        dataSource.clear();
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
