package com.alexShop.data.model

import androidx.room.Dao
import androidx.room.Query
import com.alexShop.ui.shop.StoreItem

@Dao
interface StoreItemDao {
    @Query("SELECT * FROM storeItem")
    fun loadAllItems(): Array<StoreItem>
}