package com.geekbrains.mynotes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class MyNotes implements Serializable {
    private String Name;
    private String Description;
    private String DateCreate;

    public MyNotes(String name, String description, String dateCreate) {
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
