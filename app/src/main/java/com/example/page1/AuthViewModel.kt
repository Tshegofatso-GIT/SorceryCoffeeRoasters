package com.example.page1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AuthViewModel : ViewModel(){

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()


    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    init{
        checkAuthStatus()
    }





    private fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated(auth.currentUser!!) // Pass FirebaseUser
        }
    }

    fun signInWithGoogle(credential: AuthCredential) {
        viewModelScope.launch {
            try {
                val authResult = auth.signInWithCredential(credential).await()
                val user: FirebaseUser? = authResult.user

                if (user != null) {
                    _authState.value = AuthState.Authenticated(user)
                } else {
                    _authState.value = AuthState.Error("Google Sign-In failed.")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "An error occurred.")
            }
        }
    }


    fun login(email : String,password : String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated(auth.currentUser!!)
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signup(username: String, email: String, password: String){

        if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Username, Email or password can't be empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated(auth.currentUser!!)
                }else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

sealed class AuthState{
    data class Authenticated(val user: FirebaseUser) : AuthState() // Add FirebaseUser parameter
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message : String) : AuthState()

}}