package com.geekbrains.mynotes;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.mbms.StreamingServiceInfo;

import java.io.Serializable;
import java.util.Date;

public class CardMyNotes implements Serializable {
    private String id;          // идентификатор
    private String name;        // имя заметки
    private String description; // описание
    private String dateCreate;  // дата создания

    public CardMyNotes() {}

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public CardMyNotes(String id, String name, String description, String dateCreate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateCreate = dateCreate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreate() {
        return dateCreate;
    }

}
