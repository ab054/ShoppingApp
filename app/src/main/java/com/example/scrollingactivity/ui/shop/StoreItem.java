package com.example.scrollingactivity.ui.shop;

class StoreItem {
    public String name;
    public int imageResourse;
    public String description;
    public int cost;
    public boolean purchased;


    public StoreItem(String name, int imageResourse, String description, int cost, boolean purchased) {
        this.name = name;
        this.imageResourse = imageResourse;
        this.description = description;
        this.cost = cost;
        this.purchased = purchased;
    }

}
