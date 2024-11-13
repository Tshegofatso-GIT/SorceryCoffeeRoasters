package com.example.page1.pages



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.page1.AuthViewModel
import com.example.page1.R


@Composable
fun WelcomePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel){
    Scaffold(
        containerColor = Color(0xFF004D40) // Dark green background
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) { // Box to hold the image and content

            Image(
                painter = painterResource(id = R.drawable.intro_pic), // Your image
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop, // Adjust how the image is scaled
                modifier = Modifier.fillMaxSize()
            )

            // Add a gradient overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF004D40), // Your dark green color
                                Color(0x00000000)  // Transparent color
                            )
                        )
                    )
            )



        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally)
        {

            // App Title
            Text(
                text = "Sorcery Coffee Roaster-kate was here",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // App Tagline
            Text(
                text = "Skip the line, \n get the coffee",
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Sign Up Button
            Button(
                onClick = { navController.navigate("signup") },
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B)) // Lighter green
            ) {
                Text("Sign Up", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Log In Button
            Button(
                onClick = {  navController.navigate("login") },
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)) // white color
            ) {
                Text("Login", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        }
    }
}















//welcome page content




