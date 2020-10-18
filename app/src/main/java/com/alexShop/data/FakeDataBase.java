package com.alexShop.data;

import com.alexShop.R;
import com.alexShop.ui.shop.StoreItem;

import java.util.ArrayList;
import java.util.Arrays;

class FakeDataBase {

    static ArrayList data = new ArrayList<>(Arrays.asList(
        new StoreItem("Bone", R.drawable.bone, "Good chew toy.", 1, true),
        new StoreItem("Carrot", R.drawable.carrot, "Good chew.", 1, false),
        new StoreItem("Dog", R.drawable.dog, "Chews toy.", 2, false),
        new StoreItem("Flame", R.drawable.flame, "Brown fox just jumps around with no use", 999, false),
        new StoreItem("Grapes", R.drawable.grapes, "Your eat them.", 1, false),
        new StoreItem("House", R.drawable.house, "As opposed to home.", 100, false),
        new StoreItem("Lamp", R.drawable.lamp, "It lights.", 2, false),
        new StoreItem("Mouse", R.drawable.mouse, "Not a rat.", 1, false),
        new StoreItem("Nail", R.drawable.nail, "Hammer required.", 1, false),
        new StoreItem("Penguin", R.drawable.penguin, "Find Batman.", 10, false),
        new StoreItem("Rocks", R.drawable.rocks, "Rolls.", 1, false),
        new StoreItem("Star", R.drawable.star, "Like the sun but farther away.", 25, false),
        new StoreItem("Toad", R.drawable.toad, "Like a frog.", 1, false),
        new StoreItem("Van", R.drawable.van, "Has four wheels.", 10, false),
        new StoreItem("Wheat", R.drawable.wheat, "Some breads have it.", 1, false),
        new StoreItem("Yak", R.drawable.yak, "Yakity Yak Yak.", 15, false)));

    public static ArrayList<StoreItem> getData() {
        return data;
    }
}
