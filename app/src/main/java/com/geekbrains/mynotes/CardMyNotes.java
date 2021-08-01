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
    private Date dateCreate;  // дата создания

    public CardMyNotes(String s) {}

    protected CardMyNotes(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        dateCreate = new Date(in.readLong());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public CardMyNotes(String name, String description, Date dateCreate) {
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

    public Date getDateCreate() {
        return dateCreate;
    }

}
