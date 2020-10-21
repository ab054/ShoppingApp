package com.alexShop.data.daos

import androidx.room.*
import com.alexShop.ui.shop.StoreItem

@Dao
interface StoreItemDao {
    @Query("SELECT * FROM storeItem WHERE quantity > 0")
    fun getAvailable(): Array<StoreItem>

    @Query("UPDATE storeItem SET quantity = quantity - 1 WHERE name = :itemName")
    fun setItemPurchased(itemName: String)

    @Query("SELECT * FROM storeItem")
    fun loadAllItems(): Array<StoreItem>

    @Update
    fun updateItems(item: StoreItem)

    @Delete
    fun deleteItem(item: StoreItem)

    @Query("DELETE FROM StoreItem")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(item: StoreItem)

}