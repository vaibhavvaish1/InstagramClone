package com.infinitysolutions.instagramclone

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
  @Provides
  fun providesAuthetication(): FirebaseAuth = Firebase.auth

  @Provides
  fun providesFirestore(): FirebaseFirestore = Firebase.firestore

  @Provides
  fun providesFireStorage(): FirebaseStorage = Firebase.storage
}