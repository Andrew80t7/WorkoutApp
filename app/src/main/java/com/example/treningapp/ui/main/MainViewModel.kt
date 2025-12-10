// MainViewModel.kt - для навигации и общего состояния
package com.example.treningapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treningapp.domain.UserRepository
import com.example.treningapp.navigation.Screen
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    // Состояние аутентификации
    val isLoggedIn = userRepository.isUserLoggedIn
    val currentUser = userRepository.getUserProfile()

    // Навигация
    private val _navigationState = MutableStateFlow<Screen>(Screen.First)
    val navigationState: StateFlow<Screen> = _navigationState

    fun navigateTo(screen: Screen) {
        _navigationState.value = screen
    }
}