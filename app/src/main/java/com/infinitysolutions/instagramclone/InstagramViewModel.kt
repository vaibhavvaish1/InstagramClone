package com.infinitysolutions.instagramclone

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.infinitysolutions.instagramclone.auth.data.Event
import com.infinitysolutions.instagramclone.auth.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class InstagramViewModel @Inject constructor(
  val auth: FirebaseAuth,
  val db: FirebaseFirestore,
  val storage: FirebaseStorage,
) : ViewModel() {

  val signedIn = mutableStateOf(false)
  val inProgress = mutableStateOf(false)
  val userData = mutableStateOf<UserData?>(null)
  val popUpNotification = mutableStateOf<Event<String>?>(null)

  init {
    auth.signOut()
    val currentUser = auth.currentUser
    signedIn.value = currentUser != null
    currentUser?.uid?.let { uid ->
      getUserData(uid)
    }
  }

  fun signUp(userName: String, email: String, password: String) {
    if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
      handleException(customMessage = "Please fill all given fields")
      return
    }
    inProgress.value = true
    db.collection("USERS").whereEqualTo("username", userName).get()
      .addOnSuccessListener { documents ->
        if (documents.size() > 0) {
          handleException(customMessage = "Username already exists")
          inProgress.value = false
        } else {
          auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
              if (task.isSuccessful) {
                signedIn.value = true
                createOrUpdateProfile(username = userName)
              } else {
                handleException(task.exception, "Sign Up Failed")
              }
              inProgress.value = false
            }
        }

      }.addOnFailureListener { exception ->
        Log.e("SignUpError", "Failed to check username: ${exception.message}", exception)
        handleException(exception, "Failed to check username")
        inProgress.value = false
      }
  }

  fun login(email: String, password: String) {
    if (email.isEmpty() || password.isEmpty()) {
      handleException(customMessage = "Please fill all fields")
      return
    }
    inProgress.value = true
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        signedIn.value = true
        inProgress.value = false
        auth.currentUser?.uid?.let { uid ->
          getUserData(uid)
          handleException(customMessage = "Login successful")
        }
      }
    }.addOnFailureListener { exc ->
      handleException(exc, "Login Failed")
      inProgress.value = false

    }
  }

  fun handleException(exception: Exception? = null, customMessage: String = "") {
    exception?.printStackTrace()
    val errorMsg = exception?.localizedMessage ?: ""
    val message = if (customMessage.isEmpty()) errorMsg else "$customMessage : $errorMsg"
    popUpNotification.value = Event(message)
  }

  private fun getUserData(uid: String) {
    inProgress.value = true
    db.collection("USERS").document(uid).get().addOnSuccessListener {
      val user = it.toObject<UserData>()
      userData.value = user
      inProgress.value = false
      popUpNotification.value = Event("User data retrieved successfully")

    }.addOnFailureListener { exc ->
      handleException(exc, "cannot retrieve user data")
      inProgress.value = false
    }

  }

  private fun createOrUpdateProfile(
    name: String? = null,
    username: String? = null,
    bio: String? = null,
    imageUrl: String? = null,
  ) {
    val uid = auth.currentUser?.uid
    val userData = UserData(
      userId = uid,
      name = name ?: userData.value?.name,
      userName = username ?: userData.value?.userName,
      bio = bio ?: userData.value?.bio,
      imageUrl = imageUrl ?: userData.value?.imageUrl,
      following = userData.value?.following
    )

    uid?.let {
      inProgress.value = true
      db.collection("USERS").document(uid).get().addOnSuccessListener {
        if (it.exists()) {
          it.reference.update(userData.toMap()).addOnSuccessListener {
              this.userData.value = userData
              inProgress.value = false
            }.addOnFailureListener {
              handleException(it, "Cannot update user details")
              inProgress.value = false
            }
        } else {
          db.collection("USERS").document(uid).set(userData)
          getUserData(uid)
          inProgress.value = false
        }
      }.addOnFailureListener { exc ->
        handleException(exc, "cannot create user")
        inProgress.value = false
      }
    }
  }
}
