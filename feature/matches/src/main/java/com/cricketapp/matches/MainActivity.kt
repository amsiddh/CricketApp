package com.cricketapp.matches

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cricketapp.matches.navigation.NavigationGraph
import com.cricketapp.matches.theme.CricketAppTheme
import com.cricketapp.matches.theme.LightColorScheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val appBarTitle = remember {
                mutableStateOf("")
            }
            CricketAppTheme {
                Scaffold(
                    topBar = {
                        AppBar(navController = navController, appBarTitle.value)
                    },
                    content = { padding ->
                        NavigationGraph(modifier = Modifier.padding(padding), navController = navController) {
                            appBarTitle.value = it
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController, appBarTitle: String) {
    TopAppBar(
        title = { Text(text = appBarTitle) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            LightColorScheme.primary
        ),
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        }
    )
}
