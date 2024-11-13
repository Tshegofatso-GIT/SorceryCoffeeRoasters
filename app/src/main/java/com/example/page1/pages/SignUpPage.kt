package com.example.page1.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import com.example.page1.AuthViewModel.AuthState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.page1.AuthViewModel
import com.example.page1.R

@Composable
fun SignUpPage(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {

    var username by remember {
        mutableStateOf("") }

    var email by remember {
        mutableStateOf("") }

    var password by remember {
        mutableStateOf("") }



    val navController = rememberNavController()
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> navController.navigate(route = "home")
            {
                popUpTo("signup") {
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



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(text="Create New Account",fontSize=32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White)
        Spacer(modifier=Modifier.height(24.dp))

        OutlinedTextField(value =username,
            onValueChange = {
                username = it
            },
            label = {
                Text(text = "Name",color = Color.White)
            })
        Spacer(modifier=Modifier.height(16.dp))


        OutlinedTextField(value =email ,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email", color =Color.White)
            })

        Spacer(modifier=Modifier.height(16.dp))

        OutlinedTextField(value =password ,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Password",color = Color.White)
            })
        Spacer(modifier=Modifier.height(16.dp))



        Button(onClick ={
            authViewModel.signup(username,email,password)
        },      modifier = Modifier.width(250.dp),
        ) {

            Text(text = "Sign up",color = Color.White)
        }


        TextButton(onClick = {
            navController.navigate("login")
        }) {
            Text(text = "Already have an account?, Log In",color=Color.White)
        }
    }
}}