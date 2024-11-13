package com.example.page1

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.page1.pages.SplashScreen
import com.example.page1.pages.HomePage
import com.example.page1.pages.LoginPage
import com.example.page1.pages.SignUpPage
import com.example.page1.pages.WelcomePage

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "welcome",builder = {
        composable("splash"){
            SplashScreen(navController)
        }

        composable("welcome"){
            WelcomePage(modifier = modifier,navController,authViewModel)
        }


        composable("login"){
            LoginPage(modifier = modifier,navController,authViewModel)
        }

        composable("signup"){
            SignUpPage(modifier = modifier, authViewModel)
        }

        composable("home"){
            HomePage(modifier = modifier,navController,authViewModel)
        }
    })
}


