package com.example.nestko.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    // private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun signInWithEmail(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            // MOCK LOGIN LOGIC
            delay(2000)
            if (email == "error@test.com") {
                _authState.value = AuthState.Error("Invalid credentials")
            } else {
                _authState.value = AuthState.Success
            }
        }
    }

    fun signUpWithEmail(email: String, password: String, name: String) {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            _authState.value = AuthState.Error("All fields must be filled")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            // MOCK SIGN UP LOGIC
            delay(2000)
            _authState.value = AuthState.Success
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}
