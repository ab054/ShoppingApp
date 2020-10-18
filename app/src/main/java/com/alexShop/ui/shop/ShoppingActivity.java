package com.alexShop.ui.shop;

import static com.alexShop.ui.shop.ShopData.addItem;
import static com.alexShop.ui.shop.ShopData.getAvailableItems;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.alexShop.R;
import com.alexShop.ui.login.LoginViewModel;
import com.alexShop.ui.login.LoginViewModelFactory;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShoppingActivity extends AppCompatActivity {

    private static final String GET_SHOPPING_LIST = "http://10.0.2.2:5005/list";
    private RecyclerView recyclerView;
    private ItemListAdapter itemListAdapter;
    private MenuItem searchItem;
    private SearchView searchView;
    private static MyCustomDialog customDialog;
    private static String purchaseItemName;
    private static int purchaseItemCost;
    private static int purchaseItemIndex;
    private TextView balanceView;
    private LoginViewModel loginViewModel;

    public static void itemClicked(int itemIndex) {
        customDialog.show();
        TextView textView = customDialog.findViewById(R.id.item_name);
        ImageView imageView = customDialog.findViewById(R.id.item_picture);
        TextView costText = customDialog.findViewById(R.id.item_cost);
        TextView dectCost = customDialog.findViewById(R.id.item_desc);

        textView.setText(getAvailableItems().get(itemIndex).name);
        purchaseItemName = getAvailableItems().get(itemIndex).name;
        purchaseItemIndex = itemIndex;
        costText.setText("$" + getAvailableItems().get(itemIndex).cost);
        purchaseItemCost = getAvailableItems().get(itemIndex).cost;
        dectCost.setText(getAvailableItems().get(itemIndex).description);
        imageView.setImageResource(getAvailableItems().get(itemIndex).imageResource);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        StringRequest request = new StringRequest(Request.Method.GET, GET_SHOPPING_LIST, getListener(),
            getErrorListener());
        setUpRecyclerView();
        customDialog = new MyCustomDialog(this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

        logoutBtn();
        addMoneyBtn();
    }

    private void logoutBtn() {
        Button logoutButton = findViewById(R.id.btn_logout);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
            .get(LoginViewModel.class);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.logout();
                finish();
            }
        });
    }

    private void addMoneyBtn() {
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
    }

    @NotNull
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        itemListAdapter = new ItemListAdapter(this, getAvailableItems());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemListAdapter);
    }

    @NotNull
    private Response.Listener<String> getListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("g");

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray list = object.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject current = list.getJSONObject(i);

                        String name = current.getString("name");
                        String image = current.getString("image");
                        String description = current.getString("description");
                        int cost = current.getInt("cost");

                        Field idField = R.drawable.class.getDeclaredField(image);
                        int anInt = idField.getInt(idField);

                        addItem(new StoreItem(name, anInt, description, cost, false));
                    }
                } catch (JSONException e) {
                    Logger.getAnonymousLogger().log(Level.WARNING, e.getStackTrace().toString());
                } catch (NoSuchFieldException e) {
                    Logger.getAnonymousLogger().log(Level.WARNING, e.getStackTrace().toString());
                } catch (IllegalAccessException e) {
                    Logger.getAnonymousLogger().log(Level.WARNING, e.getStackTrace().toString());
                }

            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        if (id == R.id.sort_name_desc) {
            itemListAdapter.sortByNameDesc();
        }

        if (id == R.id.sort_name_asc) {
            itemListAdapter.sortByNameAsc();
        }

        if (id == R.id.sort_cost_desc) {
            itemListAdapter.sortByCostDesc();
        }

        if (id == R.id.sort_cost_asc) {
            itemListAdapter.sortByCostAsc();
        }

        if (id == R.id.sort_date_desc) {
            itemListAdapter.sortByDateDesc();
        }

        if (id == R.id.sort_date_asc) {
            itemListAdapter.sortByDateAsc();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);

        SearchManager searchManager = (SearchManager)
            getSystemService(Context.SEARCH_SERVICE);

        balanceView = findViewById(R.id.balance_textValue);

        searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.
            getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
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
                itemListAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    class MyCustomDialog extends Dialog {

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

            purchaseButton.setOnClickListener(new MyPurchaseClickListener(this));
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    class MyPurchaseClickListener implements View.OnClickListener {

        private final MyCustomDialog myCustomDialog;

        public MyPurchaseClickListener(MyCustomDialog myCustomDialog) {
            this.myCustomDialog = myCustomDialog;
        }

        @Override
        public void onClick(View v) {
            setItemPurchased(purchaseItemName);
            removeView();
            myCustomDialog.dismiss();
            subtractBalance();
        }

        private void removeView() {
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                View view = recyclerView.getChildAt(i);
                TextView name = view.findViewById(R.id.nameButton);
                if (name.getText().toString().equalsIgnoreCase(purchaseItemName)) {
                    recyclerView.removeView(view);
                    itemListAdapter.notifyItemRemoved(i);
                    itemListAdapter.notifyItemRangeChanged(i, recyclerView.getAdapter().getItemCount());
                    break;
                }
            }
            itemListAdapter.notifyDataSetChanged();
        }

        private void subtractBalance() {
            int valletValue = Integer.parseInt(balanceView.getText().toString());
            valletValue = valletValue - purchaseItemCost;
            balanceView.setText(String.valueOf(valletValue));
        }

        private void setItemPurchased(String itemName) {
            for (int itemID = 0; itemID < FakeDataBase.getData().size(); itemID++) {
                StoreItem currentItem = FakeDataBase.getData().get(itemID);
                if (currentItem.name.equalsIgnoreCase(itemName)) {
                    currentItem.purchased = true;
                    break;
                }
            }
        }
    }
}
