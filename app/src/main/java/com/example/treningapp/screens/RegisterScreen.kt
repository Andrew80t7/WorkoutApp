package com.example.treningapp.screens

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.treningapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: (() -> Unit)? = null,
    onGoogleSignIn: (() -> Unit)? = null,
    authViewModel: com.example.treningapp.auth.AuthViewModel? = null,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as Activity

    // Состояния для полей ввода
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(activity, gso)

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken

                if (authViewModel != null) {
                    authViewModel.firebaseAuthWithGoogle(idToken)
                } else {
                    onGoogleSignIn?.invoke()
                }
            } catch (e: ApiException) {
                if (authViewModel != null) {
                    authViewModel.firebaseAuthWithGoogle(null)
                } else {
                    onGoogleSignIn?.invoke()
                }
            }
        }

    val authState by authViewModel?.authState?.collectAsState() ?: remember {
        mutableStateOf<com.example.treningapp.auth.AuthState>(com.example.treningapp.auth.AuthState.Idle)
    }

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

                Spacer(Modifier.height(32.dp))

                // Поля ввода
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name", color = Color(0x99FFFFFF)) },
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
                    )
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color(0x99FFFFFF)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                    )
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color(0x99FFFFFF)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (showPassword) "Hide password" else "Show password",
                                tint = Color(0x99FFFFFF)
                            )
                        }
                    },
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
                    )
                )
            }

            // Центральная часть с кнопками
            Column {
                OutlinedButton(
                    onClick = {
                        val signInIntent = googleSignInClient.signInIntent
                        launcher.launch(signInIntent)
                    },
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

                Button(
                    onClick = {
                        if (authViewModel != null) {
                            // Вызываем метод регистрации по email из ViewModel
                            authViewModel.registerWithEmail(name, email, password)
                        } else {
                            // Если ViewModel не передан, используем колбэк
                            onRegisterClick?.invoke()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FFB7),
                        contentColor = Color(0xFF000000)
                    ),
                    enabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank()
                ) {
                    Text("Register", style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "By continuing you accept our Privacy Policy and",
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

    // Показываем индикатор загрузки
    if (authState is com.example.treningapp.auth.AuthState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF00FFB7))
        }
    }

    // Обработка успешной регистрации
    LaunchedEffect(authState) {
        if (authState is com.example.treningapp.auth.AuthState.Success) {
        }
    }
}