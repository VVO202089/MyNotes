package com.geekbrains.mynotes;

public interface CardMyNotesSource {
    CardMyNotes getCardNotes(int position);
    int size();
    void openCard(int position);
    void deleteCardNotes(int position);
    void updateCardNotes(int position, CardMyNotes cardNotes);
    void addCardNotes(CardMyNotes cardNotes);
    void clearCardNotes();
}
