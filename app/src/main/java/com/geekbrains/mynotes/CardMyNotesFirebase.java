package com.geekbrains.mynotes;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CardMyNotesFirebase implements CardMyNotesSource,CardMyNotesFireBaseSource {

    private static final String CARDS_COLLECTION = "notes";
    private static final String TAG = "[CardMyNotesFirebase]";

    // база данных FireStore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // коллекция документов
    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    // загружаемый список карточек
    private List<CardMyNotes> myNotesList = new ArrayList<CardMyNotes>();

    // здесь инициализация
    @Override
    public void initFireBase(final CardMyNotesResponse myNotesResponse) {

        // получить всю коллекцию, отсортированную по полю "Дата"
        //collection.orderBy(CardDataMa)
        //return new CardMyNotes("1","1","1");
    }

    @Override
    public CardMyNotes getCardNotes(int position) {
        return myNotesList.get(position);
    }

    @Override
    public int size() {
        if(myNotesList == null){
            return 0;
        }
        return myNotesList.size();
    }

    @Override
    public void openCard(int position) {

    }

    @Override
    public void deleteCardNotes(int position) {
        myNotesList.remove(position);
    }

    @Override
    public void updateCardNotes(int position, CardMyNotes cardNotes) {

    }

    @Override
    public void addCardNotes(CardMyNotes cardNotes) {
        myNotesList.add(cardNotes);
    }

    @Override
    public void clearCardNotes() {
        myNotesList.clear();
    }

    ///@Override
    //public void initialized(CardMyNotes myNotes) {

    //}

}
