// ui/screens/FirstScreen.kt
package com.example.treningapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FirstScreen(
    onGoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF171717))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Fitness Pro",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFFFFFFF)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Измени своё тело полностью",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFFFFFF)
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = onGoClick,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FFB7),
                        contentColor = Color(0xFFFFFFFF)
                    )
                ) {
                    Text("Go")
                }
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}