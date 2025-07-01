package com.infinitysolutions.instagramclone.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.infinitysolutions.instagramclone.DestinationScreen
import com.infinitysolutions.instagramclone.InstagramViewModel
import com.infinitysolutions.instagramclone.R
import com.infinitysolutions.instagramclone.utilities.CheckedSignIn
import com.infinitysolutions.instagramclone.utilities.CommonProgressSpinner
import com.infinitysolutions.instagramclone.utilities.navigateTo

@Composable
fun SignUpScreen(navController: NavController, vm: InstagramViewModel) {
  CheckedSignIn(navController = navController, vm = vm)
  val focus = LocalFocusManager.current
  val usernameState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
    mutableStateOf(TextFieldValue())
  }
  val emailState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
    mutableStateOf(TextFieldValue())
  }
  val passState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
    mutableStateOf(TextFieldValue())
  }

  val isLoading = vm.inProgress.value

  Box(modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center) {
    Column(
      modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp)
        .padding(top = 32.dp, bottom = 16.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      // App logo
      Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.instagram_icon),
        modifier = Modifier
          .width(200.dp)
          .padding(vertical = 24.dp)
      )

      // Title
      Text(
        text = "Sign Up",
        fontSize = 30.sp,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier.padding(bottom = 24.dp)
      )

      // Username Field
      OutlinedTextField(
        value = usernameState.value,
                        onValueChange = { usernameState.value = it },
                        label = { Text("Username") },
                        modifier = Modifier
                          .fillMaxWidth()
                          .padding(vertical = 8.dp)
      )

      // Email Field
      OutlinedTextField(
        value = emailState.value,
                        onValueChange = { emailState.value = it },
                        label = { Text("Email") },
                        modifier = Modifier
                          .fillMaxWidth()
                          .padding(vertical = 8.dp)
      )

      // Password Field
      OutlinedTextField(
        value = passState.value,
                        onValueChange = { passState.value = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                          .fillMaxWidth()
                          .padding(vertical = 8.dp)
      )

      // Sign Up Button
      Button(
        onClick = {
          focus.clearFocus(true)
          vm.signUp(
            usernameState.value.text.trim(), emailState.value.text.trim(), passState.value.text
          )
        }, modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 16.dp)
      ) {
        Text("SIGN UP")
      }

      // Already have account text
      Text(
        text = "Already a user? Go to Login",
           color = MaterialTheme.colorScheme.primary,
           fontSize = 14.sp,
           modifier = Modifier
             .clickable {
               navigateTo(navController, DestinationScreen.Login)
             }
             .padding(top = 8.dp))
    }

    // Loading Spinner
    if (isLoading) {
      CommonProgressSpinner()
    }
  }
}
