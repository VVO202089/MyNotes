package com.geekbrains.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class CardMyNotes implements Serializable {
    private String id;          // идентификатор
    private String Name;        // имя заметки
    private String Description; // описание
    private String DateCreate;  // дата создания

    public CardMyNotes(String name, String description, String dateCreate) {
        Name = name;
        Description = description;
        DateCreate = dateCreate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
