package com.example.scrollingactivity.ui.shop;

import java.util.ArrayList;


public class ShopData {

    private static ArrayList<StoreItem> dataList = new ArrayList<>();

    public static ArrayList<StoreItem> getAvailableItems() {
        StoreItem[] data = obtainDataFromBase();
        selectAllNotPurchased(data);
        return dataList;
    }

    public static void addItem(StoreItem item) {
        dataList.add(item);
    }

    private static void selectAllNotPurchased(StoreItem[] data) {
        for (StoreItem each : data) {
            if (!each.purchased) {
                dataList.add(each);
            }
        }
    }

    private static StoreItem[] obtainDataFromBase() {
        return FakeDataBase.getData();
    }
}
