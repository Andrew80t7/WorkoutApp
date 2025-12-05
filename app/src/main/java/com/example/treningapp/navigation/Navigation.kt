package com.example.treningapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.activity.compose.BackHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.treningapp.auth.AuthState
import com.example.treningapp.auth.AuthViewModel
import com.example.treningapp.screens.FirstScreen
import com.example.treningapp.screens.RegisterScreen
import com.example.treningapp.screens.LoginScreen
import com.example.treningapp.screens.ProfileSetupScreen

sealed class Screen {
    object First : Screen()
    object Register : Screen()
    object Login : Screen()
    object ProfileSetup : Screen()
    object Home : Screen()
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsState()
    val currentScreen = remember { mutableStateOf<Screen>(Screen.First) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Обработка успешной аутентификации
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                // Переходим на экран профиля при успешной регистрации/логине
                currentScreen.value = Screen.ProfileSetup
            }
            is AuthState.Error -> {
                val msg = (authState as AuthState.Error).message ?: "Authentication error"
                snackbarHostState.showSnackbar(msg)
            }
            else -> {}
        }
    }

    // Обработка кнопки "Назад"
    BackHandler(enabled = currentScreen.value != Screen.First) {
        when (currentScreen.value) {
            Screen.Register, Screen.Login -> currentScreen.value = Screen.First
            Screen.ProfileSetup -> currentScreen.value = Screen.First
            else -> {}
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        when (currentScreen.value) {
            Screen.First -> FirstScreen(
                onGoClick = { currentScreen.value = Screen.Register },
                modifier = modifier.padding(innerPadding)
            )

            Screen.Register -> RegisterScreen(
                onBack = { currentScreen.value = Screen.First },
                onLoginClick = { currentScreen.value = Screen.Login },
                authViewModel = authViewModel,
                modifier = modifier.padding(innerPadding)
            )

            Screen.Login -> LoginScreen(
                onBack = { currentScreen.value = Screen.First },
                onRegisterClick = { currentScreen.value = Screen.Register },
                onLoginSuccess = { /* Обрабатывается через authState */ },
                authViewModel = authViewModel,
                modifier = modifier.padding(innerPadding)
            )

            Screen.ProfileSetup -> ProfileSetupScreen(
                onBack = { currentScreen.value = Screen.First },
                onNext = {
                    // После завершения профиля переходим на главный экран
                    currentScreen.value = Screen.Home
                },
                modifier = modifier.padding(innerPadding)
            )

            Screen.Home -> FirstScreen(
                onGoClick = { /* Ничего не делаем, мы уже на главном */ },
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}