package com.geekbrains.mynotes;

import java.util.List;

public class NotesSourceImpl implements CardMyNotes_Source{

    private List<Card_MyNotes>dataSource;

    public NotesSourceImpl(List<Card_MyNotes> dataSource) {
        this.dataSource = dataSource;
    }

    public  NotesSourceImpl init(){
        // для теста
        Card_MyNotes myNotes1 = new Card_MyNotes("Notes1", "Тестовая первая заметка", "01.01.2021");
        Card_MyNotes myNotes2 = new Card_MyNotes("Notes2", "Тестовая вторая заметка", "02.01.2021");
        Card_MyNotes myNotes3 = new Card_MyNotes("Notes3", "Тестовая третья заметка", "03.01.2021");
        dataSource.add(myNotes1);
        dataSource.add(myNotes2);
        dataSource.add(myNotes3);

        return this;
    }


    @Override
    public Card_MyNotes getCardNotes(int position) {
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
    public void updateCardNotes(int position, Card_MyNotes cardNotes) {
        dataSource.set(position,cardNotes);
    }

    @Override
    public void addCardNotes(Card_MyNotes cardNotes) {
        dataSource.add(cardNotes);
    }

    @Override
    public void clearCardNotes() {
        dataSource.clear();
    }
}
