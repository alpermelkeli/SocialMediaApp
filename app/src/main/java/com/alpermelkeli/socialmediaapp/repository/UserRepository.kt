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
    fun searchUsersByUsername(username: String, callBack: (List<User>) -> Unit) {
        db.collection("Users")
            .whereGreaterThanOrEqualTo("username", username)
            .whereLessThanOrEqualTo("username", username + '\uf8ff')
            .get()
            .addOnSuccessListener { result ->
                val userList = result.documents.mapNotNull { document ->
                    val id = document.id
                    val profilePhoto = document.getString("profilePhoto") ?: "error"
                    val username = document.getString("username") ?: "error"
                    val about = document.getString("about") ?: "error"
                    val followersIds = document.get("followers") as? List<String> ?: emptyList()
                    val followingIds = document.get("following") as? List<String> ?: emptyList()

                    User(
                        id = id,
                        username = username,
                        profilePhoto = profilePhoto,
                        followers = followersIds,
                        following = followingIds,
                        about = about
                    )
                }
                callBack(userList)
            }
            .addOnFailureListener {
                callBack(emptyList())
            }
    }
}