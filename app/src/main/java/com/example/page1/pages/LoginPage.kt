package com.example.page1.pages

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.page1.AuthViewModel
import com.example.page1.AuthViewModel.AuthState
import com.example.page1.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun LoginPage(modifier: Modifier = Modifier,navController: NavController,authViewModel: AuthViewModel) {



    var email by remember {
        mutableStateOf("") }

    var password by remember {
        mutableStateOf("") }


    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(route = "home")
            {
                popUpTo("login") {
                    inclusive = true
                }
            }
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize()) { // Box to hold image and content

        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Background Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
//
        //Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF004D40),  // Dark green
                            Color(0x00000000)   // Transparent
                        )
                    )
                )
        )

        // Google Sign-In variables
        var googleSignInLoading by remember { mutableStateOf(false) }
        val context = LocalContext.current
        val token = "195752437144-df2hhbppetd363jgsrsoreg1tq5tj6ue.apps.googleusercontent.com" //web client id

        // Launcher for Google Sign-In activity
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            googleSignInLoading = false
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!,
                null)
                authViewModel.signInWithGoogle(credential)

            } catch (e: ApiException) {
                Toast.makeText(context, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }




        Column(
        modifier=modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text="Login Page",fontSize=32.sp,color = Color.White)
        Spacer(modifier=Modifier.height(16.dp))

            //Email
        OutlinedTextField(value =email ,
            onValueChange = {
            email = it
        },
            label = {
                Text(text = "Email",color = Color.White)
            })

        Spacer(modifier=Modifier.height(16.dp))

// Password
        OutlinedTextField(value =password ,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Password",color = Color.White)
            })
        Spacer(modifier=Modifier.height(16.dp))

         //LOGIN button

            Button(
                onClick = { authViewModel.login(email, password) },
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text(text = "Sign In", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            //sign in with googlee
            Button(
                onClick = {
                    googleSignInLoading = true
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(token)
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)

                },
                modifier = Modifier.fillMaxWidth(0.8f),

            enabled = !googleSignInLoading, // Disable button while loading
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
            if (googleSignInLoading) {
                CircularProgressIndicator(color = Color.Black) // Show loading indicator
            } else {
                Text(text = "Sign Up with Google", color = Color.Black)
            }
        }


            Spacer(modifier=Modifier.height(8.dp))
        TextButton(onClick = {
            navController.navigate("signup")
        }) {
            Text(text = "Don't have an account \n Create one", color = Color.Black, fontSize = 18.sp)
        }
    }
}}


