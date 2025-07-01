package com.infinitysolutions.instagramclone

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.infinitysolutions.instagramclone.main.FeedScreen
import com.infinitysolutions.instagramclone.main.SignUpScreen
import com.infinitysolutions.instagramclone.ui.theme.InstagramCloneTheme
import com.infinitysolutions.instagramclone.utilities.NotificationMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      InstagramCloneTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
          InstagramApp()
        }
      }
    }
  }
}

sealed class DestinationScreen(val route: String) {
  object SignUp : DestinationScreen("signup")
  object Login : DestinationScreen("login")
  object Feed:  DestinationScreen("feed")
}

@Composable
fun InstagramApp() {
  val vm = hiltViewModel<InstagramViewModel>()
  val navController = rememberNavController()
  NotificationMessage(vm = vm)
  NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route) {
    composable(DestinationScreen.SignUp.route) {
      SignUpScreen(navController = navController, vm = vm)
    }
    composable(DestinationScreen.Login.route){
      LoginScreen(navController =  navController, vm = vm)
    }
    composable (DestinationScreen.Feed.route){
      FeedScreen(navController = navController, vm =vm)
    }
  }
}
