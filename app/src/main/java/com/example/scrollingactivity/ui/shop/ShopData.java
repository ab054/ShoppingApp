package com.example.scrollingactivity.ui.shop;

import java.util.ArrayList;


public class ShopData {

    private static ArrayList<StoreItem> dataList;

    public static ArrayList<StoreItem> getAvailableItems() {
        StoreItem[] data = obtainDataFromBase();
        selectAllNotPurchased(data);
        return dataList;
    }

    private static void selectAllNotPurchased(StoreItem[] data) {
        dataList = new ArrayList<>();
        for (StoreItem each : data) if (!each.purchased) dataList.add(each);
    }

    private static StoreItem[] obtainDataFromBase() {
        return FakeDataBase.getData();
    }
}
