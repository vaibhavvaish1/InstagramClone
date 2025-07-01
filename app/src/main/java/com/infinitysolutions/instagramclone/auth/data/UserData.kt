package com.infinitysolutions.instagramclone.auth.data

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration

data class UserData(
  var userId: String? = null,
  var name: String? = null,
  var userName: String? = null,
  var imageUrl: String? = null,
  var bio: String? = null,
  var following: List<String>? = null,
  ) {
  fun toMap() = mapOf(
    "userId" to userId,
    "name" to name,
    "username" to userName,
    "imageUrl" to imageUrl,
    "bio" to bio,
    "following" to following
    )
}

