package com.infinitysolutions.instagramclone

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InstagramCloneApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    FirebaseAuth.getInstance().firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
  }
}