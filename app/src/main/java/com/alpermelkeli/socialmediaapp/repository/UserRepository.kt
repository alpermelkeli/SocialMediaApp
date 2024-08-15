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

                val followers = fetchUsersByIds(followersIds)

                val followingIds = root.get("following") as? List<String> ?: emptyList()
                val following = fetchUsersByIds(followingIds)

                val user = User(
                    id = id,
                    username = username,
                    profilePhoto = profilePhoto,
                    followers = followers,
                    following = following,
                    posts = emptyList(),
                    about = about
                )
                callBack(user)
            } catch (e: Exception) {
                callBack(null)
            }
        }
    }

    private suspend fun fetchUsersByIds(userIds: List<String>): List<User> {
        val users = mutableListOf<User>()
        for (id in userIds) {
            val document = db.collection("Users").document(id).get().await()
            val username = document.getString("username") ?: "error"
            val profilePhoto = document.getString("profilePhoto") ?: "error"
            val about = document.getString("about") ?: "error"
            val followers = document.get("followers") as? List<String> ?: emptyList()
            val following = document.get("following") as? List<String> ?: emptyList()

            users.add(User(
                id = id,
                username = username,
                profilePhoto = profilePhoto,
                followers = emptyList(),  // To avoid deep nesting, you can skip fetching followers of followers
                following = emptyList(),  // Same for following
                posts = emptyList(),
                about = about
            ))
        }
        return users
    }
}
