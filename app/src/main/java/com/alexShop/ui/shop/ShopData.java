package com.alexShop.ui.shop;

import java.util.ArrayList;

public class ShopData {

    public static ArrayList<StoreItem> getAvailableItems() {
        return selectAllNotPurchased();
    }

    public static void addItem(StoreItem item) {
        FakeDataBase.getData().add(item);
    }

    private static ArrayList<StoreItem> selectAllNotPurchased() {
        ArrayList<StoreItem> data = FakeDataBase.getData();
        ArrayList<StoreItem> filtered = new ArrayList<>();

        for (StoreItem each : data) {
            if (!each.purchased) {
                filtered.add(each);
            }
        }

        return filtered;
    }
}
