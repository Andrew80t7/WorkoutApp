package com.example.treningapp.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val uid: String?, val displayName: String?, val email: String?) : AuthState()
    data class Error(val message: String?) : AuthState()
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    // Email/password registration
    fun registerWithEmail(name: String, email: String, password: String) {
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    // optionally update displayName
                    viewModelScope.launch {
                        user?.updateProfile(
                            com.google.firebase.auth.UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                        )?.addOnCompleteListener {
                            _authState.value =
                                AuthState.Success(user?.uid, user?.displayName, user?.email)
                        }
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.localizedMessage)
                }
            }
    }

    // Sign in with Google idToken (вызывается из Composable после получения idToken)
    fun firebaseAuthWithGoogle(idToken: String?) {
        if (idToken == null) {
            _authState.value = AuthState.Error("No id token")
            return
        }
        _authState.value = AuthState.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _authState.value = AuthState.Success(user?.uid, user?.displayName, user?.email)
                } else {
                    _authState.value = AuthState.Error(task.exception?.localizedMessage)
                }
            }
    }
    // метод для входа по почте и паролю
    fun signInWithEmail(email: String, password: String) {
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _authState.value = AuthState.Success(user?.uid, user?.displayName, user?.email)
                } else {
                    _authState.value = AuthState.Error(task.exception?.localizedMessage)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Idle
    }
}
