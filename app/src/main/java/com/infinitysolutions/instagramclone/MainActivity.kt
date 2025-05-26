package com.infinitysolutions.instagramclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.infinitysolutions.instagramclone.auth.SignUpScreen
import com.infinitysolutions.instagramclone.ui.theme.InstagramCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      InstagramCloneTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
          InstagramClone()
        }
      }
    }
  }
}

sealed class DestinationScreen(val route : String){
  object SignUp : DestinationScreen("signup")
}

@Composable
fun InstagramClone(){
  val vm = hiltViewModel<InstagramViewModel>()
  val navController = rememberNavController()
  NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route){
    composable (DestinationScreen.SignUp.route){
      SignUpScreen(navController = navController, vm =vm)
    }
  }
}
