package com.example.treningapp.domain

import com.example.treningapp.data.local.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val isUserLoggedIn: Flow<Boolean>
    val currentUserId: Flow<String?>
    fun getUserProfile(): Flow<UserEntity?>
    suspend fun login(token: String, userId: String, userProfile: UserEntity)
    suspend fun logout()


}