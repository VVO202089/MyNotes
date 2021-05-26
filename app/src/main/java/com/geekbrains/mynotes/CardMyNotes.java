package com.geekbrains.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class CardMyNotes implements Serializable {
    private final String Name;
    private final String Description;
    private final String DateCreate;

    public CardMyNotes(String name, String description, String dateCreate) {
        Name = name;
        Description = description;
        DateCreate = dateCreate;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getDateCreate() {
        return DateCreate;
    }

}
