package com.alexShop.data.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(user: User)

    @Update
    fun updateUsers(user: User)

    @Delete
    fun deleteUsers(user: User)

    @Query("SELECT * FROM user")
    fun loadAllUsers(): Array<User>
}