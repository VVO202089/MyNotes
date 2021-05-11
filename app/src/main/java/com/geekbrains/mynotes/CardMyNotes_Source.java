package com.geekbrains.mynotes;

public interface CardMyNotes_Source {
    Card_MyNotes getCardNotes(int position);
    int size();
    void deleteCardNotes(int position);
    void updateCardNotes(int position, Card_MyNotes cardNotes);
    void addCardNotes(Card_MyNotes cardNotes);
    void clearCardNotes();
}
