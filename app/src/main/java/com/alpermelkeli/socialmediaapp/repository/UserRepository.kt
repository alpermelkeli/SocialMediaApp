package com.alpermelkeli.socialmediaapp.repository

import com.alpermelkeli.socialmediaapp.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun getUserDocument(id: String, callBack: (User?) -> Unit) {
        val user = getUserDocumentAsync(id)
        callBack(user)
    }

    suspend fun getUserDocumentAsync(id: String): User? {
        return try {
            val document = db.collection("Users").document(id).get().await()
            val username = document.getString("username") ?: return null
            val profilePhoto = document.getString("profilePhoto") ?: return null
            val about = document.getString("about") ?: return null

            val followersIds = document.get("followers") as? List<String> ?: emptyList()
            val followingIds = document.get("following") as? List<String> ?: emptyList()

            User(
                id = id,
                username = username,
                profilePhoto = profilePhoto,
                followers = fetchUsersByIds(followersIds),
                following = fetchUsersByIds(followingIds),
                posts = emptyList(),
                about = about
            )
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun fetchUsersByIds(userIds: List<String>): List<User> {
        return userIds.mapNotNull { id ->
            getUserDocumentAsync(id)
        }
    }
}