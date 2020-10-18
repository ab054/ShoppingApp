package com.alexShop.data.daos

import androidx.room.*
import com.alexShop.ui.shop.StoreItem

@Dao
interface StoreItemDao {
    @Query("SELECT * FROM storeItem WHERE purchased <> 1")
    fun getAvailable(): Array<StoreItem>

    @Query("UPDATE storeItem SET purchased = 1 WHERE name = :itemName")
    fun setItemPurchased(itemName: String)

    @Query("SELECT * FROM storeItem")
    fun loadAllItems(): Array<StoreItem>

    @Update
    fun updateItems(item: StoreItem)

    @Delete
    fun deleteItem(item: StoreItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(item: StoreItem)

}