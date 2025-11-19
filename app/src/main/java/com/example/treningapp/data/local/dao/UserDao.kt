package com.example.treningapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.treningapp.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)


    @Update
    suspend fun updateUser(user: UserEntity)


    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user_profile WHERE userId = :id")
    suspend fun getUserById(id: String): Flow<UserEntity?>


}