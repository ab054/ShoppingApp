package com.example.scrollingactivity.ui.shop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.scrollingactivity.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {


    private MyCustomDialog customDialog;
    private LinearLayout scrollViewItemList;
    private int purchaseItemIndex;
    private String purchaseItemName;
    private int purchaseItemCost;
    private TextView balanceView;

    public class ShopData{
        public String name;
        public int imageResourse;
        public String description;
        public int cost;
        public boolean purchased;


        public ShopData(String name, int imageResourse, String description, int cost, boolean purchased) {
            this.name = name;
            this.imageResourse = imageResourse;
            this.description = description;
            this.cost = cost;
            this.purchased = purchased;
        }
    }

    ShopData data[] = {
            new ShopData("Bone", R.drawable.bone, "Good chew toy.", 1, false),
            new ShopData("Carrot", R.drawable.carrot, "Good chew.", 1, false),
            new ShopData("Dog", R.drawable.dog, "Chews toy.", 2, false),
            new ShopData("Flame", R.drawable.flame, "Brown fox just jumps around with no use", 999, false),
            new ShopData("Grapes", R.drawable.grapes, "Your eat them.", 1, false),
            new ShopData("House", R.drawable.house, "As opposed to home.", 100, false),
            new ShopData("Lamp", R.drawable.lamp, "It lights.", 2, false),
            new ShopData("Mouse", R.drawable.mouse, "Not a rat.", 1, false),
            new ShopData("Nail", R.drawable.nail, "Hammer required.", 1, false),
            new ShopData("Penguin", R.drawable.penguin, "Find Batman.", 10, false),
            new ShopData("Rocks", R.drawable.rocks, "Rolls.", 1, false),
            new ShopData("Star", R.drawable.star, "Like the sun but farther away.", 25, false),
            new ShopData("Toad", R.drawable.toad, "Like a frog.", 1, false),
            new ShopData("Van", R.drawable.van, "Has four wheels.", 10, false),
            new ShopData("Wheat", R.drawable.wheat, "Some breads have it.", 1, false),
            new ShopData("Yak", R.drawable.yak, "Yakity Yak Yak.", 15, false),
    };



    private void initShopItems(){
        LayoutInflater inflater = LayoutInflater.from(this);

        scrollViewItemList = findViewById(R.id.scrollView);

        balanceView = findViewById(R.id.balance_textValue);

        for(int i = 0; i < data.length; i++){
            View myShopItem = inflater.inflate(R.layout.shop_item, null);
            final int tmp_id = i;

            Button.OnClickListener click = new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClicked(tmp_id);
                }
            };


            Button nameButton = myShopItem.findViewById(R.id.nameButton);
            nameButton.setText(data[i].name);

            Button costButton = myShopItem.findViewById(R.id.costButton);
            costButton.setText("$" + data[i].cost);

            costButton.setOnClickListener(click);

            ImageButton imageButton = myShopItem.findViewById(R.id.imageButton);
            imageButton.setImageResource(data[i].imageResourse);

            if (data[i].purchased == false) {
                scrollViewItemList.addView(myShopItem);
            }
        }

        customDialog = new MyCustomDialog(this);
    }

    private void itemClicked(int i) {
        customDialog.show();
        TextView textView = customDialog.findViewById(R.id.item_name);
        ImageView imageView = customDialog.findViewById(R.id.item_picture);
        TextView costText = customDialog.findViewById(R.id.item_cost);
        TextView dectCost = customDialog.findViewById(R.id.item_desc);

        textView.setText(data[i].name);
        purchaseItemName = data[i].name;
        purchaseItemIndex = i;
        costText.setText("$" + data[i].cost);
        purchaseItemCost = data[i].cost;
        dectCost.setText(data[i].description);
        imageView.setImageResource(data[i].imageResourse);

    }

    class MyCustomDialog extends Dialog{

        public MyCustomDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_box);

            Button purchaseButton = findViewById(R.id.button_purchase);
            Button cancelButton = findViewById(R.id.button_cancel);

            purchaseButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setItemPurchased(purchaseItemName);

                    for (int i = 0; i < scrollViewItemList.getChildCount(); i ++){
                        View view = scrollViewItemList.getChildAt(i);

                        TextView name = (TextView) view.findViewById(R.id.nameButton);
                        if(name.getText().toString().equalsIgnoreCase(purchaseItemName)){
                            scrollViewItemList.removeView(view);
                            break;
                        }
                    }

                    dismiss();
                    substactBalance();
                }

                private void substactBalance() {
                    int valletValue = Integer.parseInt(balanceView.getText().toString());
                    valletValue = valletValue - purchaseItemCost;
                    balanceView.setText(String.valueOf(valletValue));
                }

                private void setItemPurchased(String itemName) {
                    for(int itemID = 0; itemID < data.length;  itemID++){
                        if(data[itemID].name.equalsIgnoreCase(itemName)){
                            data[itemID].purchased = true;
                        }
                    }
                }

            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoney();

                Snackbar.make(view, "Balance increased by $100", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            private void addMoney() {
                int valletValue = Integer.parseInt(balanceView.getText().toString());
                valletValue += 100;
                balanceView.setText(String.valueOf(valletValue));
            }
        });

        initShopItems();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}