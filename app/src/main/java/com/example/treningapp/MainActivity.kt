package com.example.treningapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.treningapp.navigation.AppNavigation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.treningapp.data.local.UserPreferences
import com.example.treningapp.di.appModule
import com.example.treningapp.ui.main.HomeViewModel
import com.example.treningapp.ui.main.MainViewModel
import com.example.treningapp.ui.main.RegisterViewModel
import com.example.treningapp.ui.theme.TreningAppTheme
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TreningAppTheme {
                AppNavigation()
            }
        }
    }
}


        val userPreferences: UserPreferences by inject()

        setContent {
            TreningAppTheme {

                val viewModel: MainViewModel = koinViewModel()


                val isLoggedIn by viewModel.startDestination.collectAsState()

                when (isLoggedIn) {
                    null -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    true -> {

                        HomeScreen()
                    }

                    false -> {

                        AuthNavHost()
                    }
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
                    text = "Fitness Pro", style = MaterialTheme.typography.headlineMedium,
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

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = koinViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsState()



    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(innerPadding)
        ) {
            Text(text = "Create a new account", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )



            Spacer(Modifier.weight(1f))


            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Row {
                    Button(
                        onClick = onBack, modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Back")
                    }

                    Spacer(Modifier.width(12.dp))

                    Button(
                        onClick = {
                            if (email.isNotBlank() && password.isNotBlank()) {
                                viewModel.registerUser(email, password)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Register")
                    }
                }
            }
        }
    }
}


@Composable
fun AuthNavHost() {

    val currentScreen = remember { mutableStateOf<Screen>(Screen.First) }

    BackHandler(enabled = currentScreen.value == Screen.Register) {
        currentScreen.value = Screen.First
    }

    when (currentScreen.value) {
        Screen.First -> FirstScreen(
            onGoClick = { currentScreen.value = Screen.Register }
        )

        Screen.Register -> RegisterScreen(
            onBack = { currentScreen.value = Screen.First }

        )
    }


}




@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val user by viewModel.currentUser.collectAsState()
    Scaffold(
        bottomBar = {

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (user != null) {
                Text(
                    text = "Hello, ${user?.displayName}!",
                    style = MaterialTheme.typography.headlineMedium
                )
            } else {
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Тут будет список тренировок...", color = Color.Gray)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.logout() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Выйти из аккаунта")
            }
        }
    }




}
