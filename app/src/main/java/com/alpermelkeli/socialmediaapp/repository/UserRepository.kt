package com.alpermelkeli.socialmediaapp.repository

import com.alpermelkeli.socialmediaapp.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun getUserDocument(id:String, callBack: (User?) -> Unit) {
        id.let {
            try {
                val root = db.collection("Users")
                    .document(id)
                    .get()
                    .await()
                val profilePhoto = root.getString("profilePhoto") ?: "error"
                val username = root.getString("username") ?: "error"
                val about = root.getString("about") ?: "error"
                val followersIds = root.get("followers") as? List<String> ?: emptyList()
                val followingIds = root.get("following") as? List<String> ?: emptyList()

                val user = User(
                    id = id,
                    username = username,
                    profilePhoto = profilePhoto,
                    followers = followersIds,
                    following = followingIds,
                    about = about
                )
                callBack(user)
            } catch (e: Exception) {
                callBack(null)
            }
        }
    }
}