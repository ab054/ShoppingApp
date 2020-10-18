package com.alexShop.data.daos

import androidx.room.*
import com.alexShop.data.model.User

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