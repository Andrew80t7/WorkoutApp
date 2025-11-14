// ui/navigation/Navigation.kt
package com.example.treningapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.activity.compose.BackHandler
import com.example.treningapp.screens.FirstScreen
import com.example.treningapp.screens.RegisterScreen

sealed class Screen {
    object First : Screen()
    object Register : Screen()
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val currentScreen = remember { mutableStateOf<Screen>(Screen.First) }

    BackHandler(enabled = currentScreen.value == Screen.Register) {
        currentScreen.value = Screen.First
    }

    when (currentScreen.value) {
        Screen.First -> FirstScreen(
            onGoClick = { currentScreen.value = Screen.Register }
        )
        Screen.Register -> RegisterScreen(
            onBack = { currentScreen.value = Screen.First },
            onLoginClick = { /* Пока заглушка - можно перейти на экран логина */ },
            onRegisterClick = { /* Обработка регистрации */ },
            onGoogleSignIn = { /* Google Sign-In логика */ }
        )
    }
}