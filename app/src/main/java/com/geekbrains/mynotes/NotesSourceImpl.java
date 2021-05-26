package com.geekbrains.mynotes;

import android.view.View;

import java.util.List;

public class NotesSourceImpl implements CardMyNotesSource {
    @Override
    public void addCardNotes(CardMyNotes cardNotes) {

    }

    /*private List<CardMyNotes>dataSource;

    public NotesSourceImpl(List<CardMyNotes> dataSource) {
        this.dataSource = dataSource;
    }

    public  NotesSourceImpl init(){
        // для теста
        CardMyNotes myNotes1 = new CardMyNotes("Notes1", "Тестовая первая заметка", "01.01.2021");
        CardMyNotes myNotes2 = new CardMyNotes("Notes2", "Тестовая вторая заметка", "02.01.2021");
        CardMyNotes myNotes3 = new CardMyNotes("Notes3", "Тестовая третья заметка", "03.01.2021");
        dataSource.add(myNotes1);
        dataSource.add(myNotes2);
        dataSource.add(myNotes3);

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

    }*/
}
