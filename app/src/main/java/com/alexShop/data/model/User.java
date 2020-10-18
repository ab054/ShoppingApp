package com.alexShop.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity
public class User {
    @PrimaryKey
    @NotNull
    public String uid;

    @ColumnInfo(name = "display_name")
    public String display_name;

    public User(String uid, String display_name) {
        this.uid = uid;
        this.display_name = display_name;
    }
}