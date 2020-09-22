package com.example.scrollingactivity.ui.shop;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scrollingactivity.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ShopListViewHolder> implements Filterable {

    private final ShoppingActivity activity;
    private ItemFilter itemFilter;

    private ArrayList<StoreItem> itemList;
    private ArrayList<StoreItem> itemListFull;

    public ItemListAdapter(ShoppingActivity activity, ArrayList<StoreItem> itemList) {
        this.activity = activity;
        this.itemList = itemList;
        itemListFull = new ArrayList<>(this.itemList);
        getFilter();
    }


    @Override
    public Filter getFilter() {
        if (itemFilter == null) {
            itemFilter = new ItemFilter();
        }

        return itemFilter;
    }


    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.shop_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ShopListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListViewHolder holder, int position) {
        StoreItem item = itemList.get(position);

        holder.itemImage.setImageResource(item.imageResourse);
        holder.itemName.setText(item.name);
        holder.itemCost.setText(String.valueOf(item.cost));
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortByNameDesc() {
        itemList.sort(new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o2.name.compareToIgnoreCase(o1.name);
            }
        });
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortByNameAsc() {
        itemList.sort(new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o1.name.compareToIgnoreCase(o2.name);
            }
        });
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortByCostDesc() {
        itemList.sort(new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o2.cost - o1.cost;
            }
        });
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortByCostAsc() {
        itemList.sort(new Comparator<StoreItem>() {
            @Override
            public int compare(StoreItem o1, StoreItem o2) {
                return o1.cost - o2.cost;
            }
        });
        notifyDataSetChanged();
    }


    class ShopListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageButton itemImage;
        final Button itemName;
        private final Button itemCost;

        ShopListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.imageButton);
            itemName = itemView.findViewById(R.id.nameButton);
            itemCost = itemView.findViewById(R.id.costButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<StoreItem> tempList = new ArrayList<>();

                for (StoreItem item : itemListFull) {
                    if (item.name.contains(constraint)) {
                        tempList.add(item);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;

            } else {
                filterResults.count = itemListFull.size();
                filterResults.values = itemListFull;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    }

}
