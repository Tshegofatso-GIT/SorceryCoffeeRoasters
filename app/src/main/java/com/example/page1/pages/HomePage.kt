package com.example.page1.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.page1.AuthViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authState = authViewModel.authState.observeAsState()



    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthViewModel.AuthState.Unauthenticated -> navController.navigate("welcome")
            else -> Unit
        }
    }

    var searchText by remember { mutableStateOf("") }
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Cart", "Favorite")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.ShoppingCart, Icons.Filled.Favorite)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Coffee Shop") },
                actions = {
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("find your favourites") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Search
                        )
                    )
                }
            )
        },

        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(icons[index], contentDescription = item) },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
       }
    )



    { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Home Page", fontSize = 32.sp)

            TextButton(onClick = {
                authViewModel.signout()
            }) {
                Text(text = "Sign out")
            }
        }
    }
}

//cartpage

