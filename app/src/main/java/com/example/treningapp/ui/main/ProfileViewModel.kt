package com.example.treningapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.treningapp.data.local.UserRepository
import com.example.treningapp.data.local.entities.UserEntity
import com.example.treningapp.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

data class ProfileUiState(
    val user: UserEntity? = null,
    val loading: Boolean = false,
    val error: String? = null
)

class ProfileViewModel(
    private val repo: UserRepository

) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun loadUser(userId: String) {
        _uiState.value = _uiState.value.copy(loading = true)
        repo.getUserProfile() // adjust if your repo api differs
            .onEach { user ->
                _uiState.value = ProfileUiState(user = user, loading = false, error = null)
            }
            .launchIn(viewModelScope)
    }

    fun saveProfile(user: UserEntity) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(loading = true)
                repo.login("", user.userId, user) // or repo.saveUser(user) if available
                _uiState.value = _uiState.value.copy(loading = false, user = user, error = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(loading = false, error = e.localizedMessage)
            }
        }
    }
}
