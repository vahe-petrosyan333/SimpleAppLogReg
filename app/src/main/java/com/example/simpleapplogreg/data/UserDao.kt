package com.example.simpleapplogreg.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(userModel: UserModel)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<UserModel>>

    @Query("SELECT EXISTS(SELECT * FROM user_table where email=:email AND pass=:pass)")
    fun login(email: String, pass: String): Boolean
}