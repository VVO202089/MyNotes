package com.geekbrains.mynotes;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class CardMyNotesMapping {

    public static class Fields {
        public final static String NAME = "Name";
        public final static String DATE = "Date";
        public final static String DESCRIPTION = "description";
    }

    public static CardMyNotes toCardMyNotes(String id, Map<String, Object> doc) {
        Timestamp timestamp = (Timestamp) doc.get(Fields.DATE);
        CardMyNotes answer = new CardMyNotes((String) doc.get(Fields.NAME)
                , (String) doc.get(Fields.DESCRIPTION), String.valueOf(timestamp.toDate()));

        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(CardMyNotes myNotes) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, myNotes.getName());
        answer.put(Fields.DESCRIPTION, myNotes.getDescription());
        answer.put(Fields.DATE,myNotes.getDateCreate());
        return answer;
    }

}
