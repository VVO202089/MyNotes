package com.geekbrains.mynotes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CardMyNotesMapping {

    public static class Fields {
        public final static String NAME = "name";
        public final static String DATE_CREATE = "dateCreate";
        public final static String DESCRIPTION = "description";
    }

    public static CardMyNotes toCardNotes(String id, Map<String, Object> doc, Date dateNotes) {
        CardMyNotes answer = new CardMyNotes(
                (String) doc.get(Fields.NAME),
                (String) doc.get(Fields.DESCRIPTION),
                dateNotes);
        answer.setId(id);
        return answer;

    }

    public static Map<String, Object> toDocument(CardMyNotes myNotes) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, myNotes.getName());
        answer.put(Fields.DATE_CREATE, myNotes.getDateCreate());
        answer.put(Fields.DESCRIPTION, myNotes.getDescription());

        return answer;

    }

}
