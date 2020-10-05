package com.example.scrollingactivity.ui.shop;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import com.example.scrollingactivity.R;
import com.example.scrollingactivity.ui.login.LoginViewModel;
import com.example.scrollingactivity.ui.login.LoginViewModelFactory;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    private LinearLayout scrollViewItemList;
    private int purchaseItemIndex;
    private String purchaseItemName;
    private int purchaseItemCost;
    private TextView balanceView;

    public StoreItem[] data = FakeDataBase.getData();
    private LoginViewModel loginViewModel;
    private MenuItem searchItem;
    private SearchView searchView;

    private void initShopItems() {
        LayoutInflater inflater = LayoutInflater.from(this);

        scrollViewItemList = findViewById(R.id.scrollView);

        balanceView = findViewById(R.id.balance_textValue);

        buildItemsList();


    }

    private void buildItemsList() {
        for (int i = 0; i < data.length; i++) {
            View myShopItem = LayoutInflater.from(this).inflate(R.layout.shop_item, null);
            final int tmp_id = i;

            Button.OnClickListener click = new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //    itemClicked(tmp_id);
                }
            };


            Button nameButton = myShopItem.findViewById(R.id.nameButton);
            nameButton.setText(data[i].name);

            Button costButton = myShopItem.findViewById(R.id.costButton);
            costButton.setText("$" + data[i].cost);

            costButton.setOnClickListener(click);

            ImageButton imageButton = myShopItem.findViewById(R.id.imageButton);
            imageButton.setImageResource(data[i].imageResource);

            if (data[i].purchased == false) {
                scrollViewItemList.addView(myShopItem);
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        Button addMoneyButton = findViewById(R.id.add_money_button);
        addMoneyButton.setOnClickListener(new View.OnClickListener() {
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

        Button logoutButton = findViewById(R.id.btn_logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.logout();
                finish();
            }
        });

        initShopItems();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                initShopItems();
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

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