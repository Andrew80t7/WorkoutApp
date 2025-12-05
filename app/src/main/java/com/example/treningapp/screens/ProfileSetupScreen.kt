package com.example.treningapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.treningapp.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ProfileSetupScreen(
    onBack: () -> Unit = {},
    onNext: () -> Unit = {},
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
            Column {
                Spacer(Modifier.height(














































































                    40.dp))

                Image(
                    painter = painterResource(id = R.drawable.haftor),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp) // Настройте высоту по необходимости
                        .padding(horizontal = 50.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )

                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Let's complete your profile",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "It will help us to know more about you!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0x99FFFFFF),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(40.dp))

                // Поля профиля
                ProfileField(title = "Choose Gender")
                Spacer(Modifier.height(16.dp))
                ProfileField(title = "Date of Birth")
                Spacer(Modifier.height(16.dp))
                ProfileField(title = "Your Weight")
            }

            // Нижняя часть с кнопкой
            Column {
                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FFB7),
                        contentColor = Color(0xFF000000)
                    )
                ) {
                    Text("Next", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun ProfileField(title: String) {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        label = { Text(title, color = Color(0x99FFFFFF)) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color(0xFF00FFB7),
            focusedLabelColor = Color(0xFF00FFB7),
            unfocusedLabelColor = Color(0x99FFFFFF),
            focusedIndicatorColor = Color(0xFF00FFB7),
            unfocusedIndicatorColor = Color(0x99FFFFFF)
        ),
        shape = RoundedCornerShape(12.dp)
    )
}