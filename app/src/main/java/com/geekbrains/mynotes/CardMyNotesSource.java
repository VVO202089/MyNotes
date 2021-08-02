package com.geekbrains.mynotes;

import java.util.ArrayList;
import java.util.List;

public interface CardMyNotesSource {

    CardMyNotes getCardNotes(int position);
    int size();
    void openCard(int position);
    void deleteCardNotes(int position);
    void clearCardNotes();
    void updateList();
}
