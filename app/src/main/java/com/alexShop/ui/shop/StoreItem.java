package com.alexShop.ui.shop;

import android.annotation.SuppressLint;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Entity
public class StoreItem {

    public Date dateAdded;
    @PrimaryKey
    @NotNull
    public String name;
    public int imageResource;
    public String description;
    public int cost;
    public int quantity;

    public StoreItem(String name, int imageResource, String description, int cost, int quantity) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.cost = cost;
        this.quantity = quantity;
        this.dateAdded = randomDate();
    }

    @SuppressLint("NewApi")
    private Date randomDate() {
        long aDay = TimeUnit.DAYS.toMillis(1);
        long now = new Date().getTime();
        Date hundredYearsAgo = new Date(now - aDay * 365 * 100);
        Date tenDaysAgo = new Date(now - aDay * 10);
        long startMillis = hundredYearsAgo.getTime();
        long endMillis = tenDaysAgo.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
            .current()
            .nextLong(startMillis, endMillis);

        return new Date(randomMillisSinceEpoch);
    }

}
