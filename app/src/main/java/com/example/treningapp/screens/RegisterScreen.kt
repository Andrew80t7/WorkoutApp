// ui/screens/RegisterScreen.kt
package com.example.treningapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF171717))
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Верхняя часть
            Column {
                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Hey body,",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFFFFFFF)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Create a new account",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFFFFFFF)
                )
            }

            Column {
                // Кнопка регистрации через Google
                OutlinedButton(
                    onClick = onGoogleSignIn,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White,
                        containerColor = Color.Transparent
                    ),
                    border = BorderStroke(1.dp, Color(0xFF00FFB7))
                ) {
                    Text("Continue with Google")
                }

                Spacer(Modifier.height(24.dp))

                // Основная кнопка регистрации
                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FFB7),
                        contentColor = Color(0xFF000000)
                    )
                ) {
                    Text("Register", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(Modifier.height(16.dp))

                // Текст с политикой конфиденциальности
                Text(
                    text = "Вы соглашаетесь с политикой конфиденциальности",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0x99FFFFFF),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Нижняя часть с ссылкой на логин
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0x99FFFFFF)
                )
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF00FFB7),
                    modifier = Modifier.clickable(onClick = onLoginClick)
                )
            }
        }
    }
}