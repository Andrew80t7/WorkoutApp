package com.example.treningapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.treningapp.R
import com.example.treningapp.data.local.entities.UserEntity
import com.example.treningapp.ui.main.ProfileViewModel

@Composable
fun ProfileSetupScreen(
    userId: String,
    profileViewModel: ProfileViewModel,
    onBack: () -> Unit = {},
    onNext: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Editable states
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf(com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.email ?: "") }
    var gender by rememberSaveable { mutableStateOf("") }
    var dob by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(userId) {
        profileViewModel.loadUser(userId)
    }

    LaunchedEffect(uiState.user) {
        uiState.user?.let { u ->
            name = u.displayName ?: name
            email = u.email ?: email
            weight = u.currentWeight?.toString() ?: weight
            height = u.height?.toString() ?: height
            age = if (u.age != 0) u.age.toString() else age
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let { snackbarHostState.showSnackbar(it) }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF171717))
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Spacer(Modifier.height(24.dp))
                Image(
                    painter = painterResource(id = R.drawable.haftor),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 40.dp),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit
                )
                Spacer(Modifier.height(24.dp))
                Text("Let's complete your profile", style = MaterialTheme.typography.headlineSmall, color = Color.White, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Spacer(Modifier.height(8.dp))
                Text("It will help us to know more about you!", style = MaterialTheme.typography.bodyMedium, color = Color(0x99FFFFFF), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full name", color = Color(0x99FFFFFF)) },
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
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color(0x99FFFFFF)) },
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
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)", color = Color(0x99FFFFFF)) },
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
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text("Height (cm)", color = Color(0x99FFFFFF)) },
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
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                )

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age", color = Color(0x99FFFFFF)) },
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
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
                )
            }
            Column {
                Button(
                    onClick = {
                        val userEntity = UserEntity(
                            userId = userId,
                            displayName = name.takeIf { it.isNotBlank() },
                            email = email,
                            currentWeight = weight.toFloatOrNull(),
                            height = height.toFloatOrNull(),
                            age = age.toIntOrNull() ?: 0,
                            avatarUrl = null
                        )
                        profileViewModel.saveProfile(userEntity)
                        onNext()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FFB7),
                        contentColor = Color(0xFF000000)
                    )
                ) {
                    if (uiState.loading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.Black, strokeWidth = 2.dp)
                    } else {
                        Text("Save & Continue")
                    }
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }

    if (uiState.loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF00FFB7))
        }
    }
}
