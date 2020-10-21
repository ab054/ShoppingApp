package com.alexShop.ui.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.alexShop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransactionHistoryActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        populateScrollView();

    }

    private void populateScrollView() {
        listView = findViewById(R.id.transactions_listView);
        SharedPreferences sharedPref = getSharedPreferences("Transactions", Context.MODE_PRIVATE);
        Map<String, Set<String>> all = (Map<String, Set<String>>) sharedPref.getAll();
        ArrayList<Transaction> arrayList = new ArrayList<>();
        for (Map.Entry<String, Set<String>> stringEntry : all.entrySet()) {
            String key = stringEntry.getKey();
            String price = (String) stringEntry.getValue().toArray()[0];
            String name = (String) stringEntry.getValue().toArray()[1];

            arrayList.add(new Transaction(key, name, price));
        }

        List list2 = new ArrayList();

        for (Transaction each : arrayList) {
            list2.add(each.timestamp + " " + each.itemName + " " + each.itemCost);
        }

        adapter = new ArrayAdapter(TransactionHistoryActivity.this, R.layout.transaction_item, list2);

        listView.setAdapter(adapter);

//        for(Transaction each : arrayList){
//            View item = findViewById(R.id.transaction_item);
//
//            TextView time = item.findViewById(R.id.trans_time);
//            TextView cost = item.findViewById(R.id.trans_cost);
//            TextView name = item.findViewById(R.id.trans_name);
//
//            time.setText(each.timestamp);
//            cost.setText(each.itemCost);
//            name.setText(each.itemName);
//
//            listView.addView(item);
//        }

    }

    private class Transaction {
        private final String timestamp;
        private final String itemName;
        private final String itemCost;

        public Transaction(String timestamp, String itemName, String itemCost) {
            this.timestamp = timestamp;
            this.itemName = itemName;
            this.itemCost = itemCost;
        }
    }
}
