package com.example.treningapp.data.repository

import com.example.treningapp.data.local.UserPreferences
import com.example.treningapp.data.local.dao.UserDao // Assuming you will have a UserDao
import com.example.treningapp.data.local.entities.UserEntity
import com.example.treningapp.domain.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val userPreferences: UserPreferences
) : UserRepository {

    override val isUserLoggedIn: Flow<Boolean> = userPreferences.accessToken
        .combine(userPreferences.userId) { token, id ->
            !token.isNullOrEmpty() && !id.isNullOrEmpty()
        }

    override val currentUserId: Flow<String?> = userPreferences.userId

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserProfile(): Flow<UserEntity?> {
        return userPreferences.userId.flatMapLatest { userId ->
            if (userId != null) {
                userDao.getUserById(userId)
            } else {
                flowOf(null)
            }
        }
    }


    override suspend fun login(token: String, userId: String, userProfile: UserEntity) {

        userPreferences.saveAuthToken(token, userId)

        userDao.insertUser(userProfile)
    }

    override suspend fun logout() {
        userPreferences.clearAuthData()
    }



}
