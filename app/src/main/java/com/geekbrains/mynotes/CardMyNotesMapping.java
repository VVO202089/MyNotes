package com.geekbrains.mynotes;

import java.util.Map;

public class CardMyNotesMapping {

    public static class Fields {
        public final static String NAME = "name";
        public final static String DATE_CREATE = "dateCreate";
        public final static String DESCRIPTION = "description";
    }

    public static CardMyNotes toCardNotes (String id, Map<String, Object> doc)
    {
        CardMyNotes answer = new CardMyNotes(
                (String)doc.get(Fields.NAME),
                (String)doc.get(Fields.DESCRIPTION),
                (String)doc.get(Fields.DESCRIPTION));
        answer.setId(id);
        return answer;

    }

}
