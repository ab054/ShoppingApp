package com.example.scrollingactivity.ui.shop;

import android.annotation.SuppressLint;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class StoreItem {
    public Date dateAdded;
    public String name;
    public int imageResource;
    public String description;
    public int cost;
    public boolean purchased;

    public StoreItem(String name, int imageResource, String description, int cost, boolean purchased) {
        this.name = name;
        this.imageResource = imageResource;
        this.description = description;
        this.cost = cost;
        this.purchased = purchased;
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
