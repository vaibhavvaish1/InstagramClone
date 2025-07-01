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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
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
fun LoginScreen(navController: NavController, vm: InstagramViewModel) {
  CheckedSignIn(navController = navController, vm = vm)

  val emailState = remember { mutableStateOf(TextFieldValue()) }
  val passState = remember { mutableStateOf(TextFieldValue()) }
  val focus = LocalFocusManager.current
  val isLoading = vm.inProgress.value

  Box(modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {
      // App Icon / Logo
      Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "App Logo",
        modifier = Modifier
          .width(250.dp)
          .padding(top = 32.dp)
          .align(androidx.compose.ui.Alignment.CenterHorizontally)
      )

      // Heading
      Text(
        text = "Login",
        fontSize = 32.sp,
        fontFamily = FontFamily.SansSerif,
        modifier = Modifier
          .padding(top = 24.dp, bottom = 16.dp)
          .align(androidx.compose.ui.Alignment.CenterHorizontally)
      )

      // Email field
      OutlinedTextField(
        value = emailState.value,
        onValueChange = { emailState.value = it },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        label = { Text("Email") })

      // Password field
      OutlinedTextField(
        value = passState.value,
        onValueChange = { passState.value = it },
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp),
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation()
      )

      // Login Button
      Button(
        onClick = {
          focus.clearFocus(true)
          val email = emailState.value.text.trim()
          val password = passState.value.text
          vm.login(email, password)
        }, modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 16.dp)
      ) {
        Text("Log In")
      }

      // Navigate to Signup
      Text(
        text = "New Here? Go to signup →",
        color = Color.Blue,
        modifier = Modifier
          .align(androidx.compose.ui.Alignment.CenterHorizontally)
          .clickable {
            navigateTo(navController, DestinationScreen.SignUp)
          })
    }
    if (isLoading) {
      CommonProgressSpinner()
    }
  }

}

