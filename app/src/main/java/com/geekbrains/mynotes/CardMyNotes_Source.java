package com.geekbrains.mynotes;

public interface CardMyNotes_Source {
    CardMyNotes getCardNotes(int position);
    int size();
    void deleteCardNotes(int position);
    void updateCardNotes(int position, CardMyNotes cardNotes);
    void addCardNotes(CardMyNotes cardNotes);
    void clearCardNotes();
}
