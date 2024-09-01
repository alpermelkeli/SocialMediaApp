package com.alpermelkeli.socialmediaapp.repository

import android.net.Uri
import android.util.Log
import com.alpermelkeli.socialmediaapp.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class UserRepository {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun getUserDocument(id: String): User? {
        return try {
            val root = db.collection("Users")
                .document(id)
                .get()
                .await()
            val profilePhoto = root.getString("profilePhoto") ?: "error"
            val username = root.getString("username") ?: "error"
            val about = root.getString("about") ?: "error"
            val followersIds = root.get("followers") as? List<String> ?: emptyList()
            val followingIds = root.get("following") as? List<String> ?: emptyList()

            User(
                id = id,
                username = username,
                profilePhoto = profilePhoto,
                followers = followersIds,
                following = followingIds,
                about = about
            )
        } catch (e: Exception) {
            null
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
    fun followUser(userId: String, followId: String, callBack: () -> Unit) {
        val userRef = db.collection("Users").document(userId)
        val followRef = db.collection("Users").document(followId)

        userRef.update("following", FieldValue.arrayUnion(followId))
            .addOnSuccessListener {
                followRef.update("followers", FieldValue.arrayUnion(userId))
                    .addOnSuccessListener {
                        callBack()
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                    }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    fun unfollowUser(userId: String, unfollowId: String, callBack: () -> Unit) {
        val userRef = db.collection("Users").document(userId)
        val unfollowRef = db.collection("Users").document(unfollowId)

        userRef.update("following", FieldValue.arrayRemove(unfollowId))
            .addOnSuccessListener {
                unfollowRef.update("followers", FieldValue.arrayRemove(userId))
                    .addOnSuccessListener {
                        callBack()
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                    }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    fun uploadProfilePhotoStorage(userId:String, uri:Uri, callBack: (String) -> Unit){
        val storage = FirebaseStorage.getInstance()
        val reference = storage.getReference("Users/$userId/profile/profile_photo")
        reference.putFile(uri).addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener { downloadUrl->
             callBack(downloadUrl.toString())
            }
        }

    }

    fun uploadProfilePhoto(userId:String,profilePhotoUrl:String){
        db.collection("Users")
            .document(userId)
            .update("profilePhoto", profilePhotoUrl)
            .addOnSuccessListener {
                Log.d("uploadTask", "your upload task has been completed")
            }
            .addOnFailureListener {
                Log.d("uploadTask", "your upload task has failure ${it.message}")

            }
    }

}