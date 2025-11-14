package com.example.treningapp
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.treningapp.ui.theme.TreningAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TreningAppTheme {
                // Состояние, которое хранит текущий экран.
                val currentScreen = remember { mutableStateOf<Screen>(Screen.First) }


                BackHandler(enabled = currentScreen.value == Screen.Register) {
                    currentScreen.value = Screen.First
                }

                // Отрисовка нужного экрана
                when (currentScreen.value) {
                    Screen.First -> FirstScreen(
                        onGoClick = { currentScreen.value = Screen.Register }
                    )
                    Screen.Register -> RegisterScreen(
                        onBack = { currentScreen.value = Screen.First }
                    )
                }
            }
        }
    }
}

sealed class Screen {
    object First : Screen()
    object Register : Screen()
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun FirstScreen(onGoClick: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier
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
                Text(text = "Fitness Pro", style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFFFFFFF))
                Spacer(Modifier.height(8.dp))
                Text(text = "Измени своё тело полностью", style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFFFFFFF))
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = onGoClick,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFF00FFB7),
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

@Composable
fun RegisterScreen(onBack: () -> Unit, modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(innerPadding)
        ) {
            Text(text = "Create a new account", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            Spacer(Modifier.weight(1f))
            Row {
                Button(onClick = onBack, modifier = Modifier.weight(1f)) {
                    Text("Back")
                }
                Spacer(Modifier.width(12.dp))
                Button(onClick = { /* регистрация */ }, modifier = Modifier.weight(1f)) {
                    Text("Register")
                }
            }
        }
    }
}
