package com.alexShop.tools;

import com.alexShop.ui.shop.StoreItem;

import java.util.Comparator;

public class Comparators {

    public static Comparator byName() {
        return new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o2.name.compareToIgnoreCase(o1.name);
            }
        };
    }

    public static Comparator<? super StoreItem> byNameAsc() {
        return new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o1.name.compareToIgnoreCase(o2.name);
            }
        };
    }

    public static Comparator<? super StoreItem> byCostDesc() {
        return new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o2.cost - o1.cost;
            }
        };
    }

    public static Comparator<? super StoreItem> byCostAsc() {
        return new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o1.cost - o2.cost;
            }
        };
    }

    public static Comparator<? super StoreItem> byDateDesc() {
        return new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o2.dateAdded.compareTo(o1.dateAdded);
            }
        };
    }

    public static Comparator<? super StoreItem> byDateAsc() {
        return new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o1.dateAdded.compareTo(o2.dateAdded);
            }
        };
    }
}
