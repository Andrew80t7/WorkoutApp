package com.example.treningapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treningapp.data.local.entities.UserEntity
import com.example.treningapp.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class RegisterViewModel(
    private val userRepository: UserRepository

) : ViewModel() {


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun registerUser(email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val fakeUserId = UUID.randomUUID().toString()
            val fakeToken = pass //"jwt_token_${System.currentTimeMillis()}"
            val  newUser = UserEntity(
                userId = fakeUserId,
                displayName = "John Pork",
                email = email,
                currentWeight = 175.0f,
                height = 125.0f,
                age = 88,
                avatarUrl = null
            )
            userRepository.login(fakeToken,fakeUserId,newUser)

            _isLoading.value = false
          println("Registering user: $email")
        }
    }





}