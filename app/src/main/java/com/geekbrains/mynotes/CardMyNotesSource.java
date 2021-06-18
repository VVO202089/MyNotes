package com.geekbrains.mynotes;

import java.util.ArrayList;
import java.util.List;

public interface CardMyNotesSource {

    void updateCardNotes(ArrayList<CardMyNotes> notesList);

    CardMyNotes getCardNotes(int position);

    int size();

    void openCard(int position);

    void deleteCardNotes(int position);

    void addCardNotes(CardMyNotes cardNotes);

    void clearCardNotes();
}
