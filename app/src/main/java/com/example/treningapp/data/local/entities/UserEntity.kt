package com.example.treningapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_profile")
data class UserEntity(
    @PrimaryKey val userId: String,
    val displayName: String?,
    val email: String,
    val currentWeight: Float?,
    val height: Float?,
    val age: Int,
    val avatarUrl: String?
)
