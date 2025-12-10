package com.example.treningapp.navigation

import org.koin.androidx.compose.getViewModel
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
import com.example.treningapp.ui.main.ProfileViewModel
//import com.example.treningapp.ProfileViewModel               // <- убедись, что путь верный
import com.google.firebase.auth.FirebaseAuth

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
    val profileViewModel: ProfileViewModel = getViewModel()
    val authState by authViewModel.authState.collectAsState()
    val currentScreen = remember { mutableStateOf<Screen>(Screen.First) }
    val snackbarHostState = remember { SnackbarHostState() }

    // Обработка успешной аутентификации -> переходим на ProfileSetup
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
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
            Screen.Register, Screen.Login, Screen.ProfileSetup -> currentScreen.value = Screen.First
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

            Screen.ProfileSetup -> {
                // Получаем uid текущего пользователя из FirebaseAuth
                val uid = FirebaseAuth.getInstance().currentUser?.uid
                if (uid != null) {
                    ProfileSetupScreen(
                        userId = uid,
                        profileViewModel = profileViewModel,
                        onBack = { currentScreen.value = Screen.First },
                        onNext = { currentScreen.value = Screen.Home },
                        modifier = modifier.padding(innerPadding)
                    )
                } else {
                    // если uid неожиданно null — вернёмся на старт и покажем ошибку
                    LaunchedEffect(Unit) {
                        snackbarHostState.showSnackbar("User id not available. Please sign in again.")
                        currentScreen.value = Screen.First
                    }
                }
            }

            Screen.Home -> FirstScreen(
                onGoClick = { /* Ничего не делаем, мы уже на главном */ },
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}
