package com.alpermelkeli.socialmediaapp.repository

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.internal.composableLambdaInstance
import com.alpermelkeli.socialmediaapp.model.Comment
import com.alpermelkeli.socialmediaapp.model.Post
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Collections
import java.util.UUID

class PostsRepository {
    private val db = FirebaseFirestore.getInstance()
    fun getUserHomePagePosts(followings: List<String>, callBack: (List<Post>) -> Unit) {
        if (followings.isEmpty()) {
            callBack(emptyList())
            return
        }

        db.collection("Posts")
            .whereIn("sender", followings) // Filter posts whose id is in the followings list
            .get()
            .addOnSuccessListener { querySnapshot ->
                val posts = querySnapshot.documents.mapNotNull { document ->
                    val postId = document.id
                    val sender = document.getString("sender") ?: ""
                    val images = document.get("images") as? List<String> ?: emptyList()
                    val username = document.getString("username") ?: ""
                    val profilePhoto = document.getString("senderPhoto") ?: ""
                    val timestamp = document.getLong("timestamp") ?: 0
                    Post(postId,sender,images,profilePhoto,username,timestamp)
                }
                val sortedPosts = posts.sortedByDescending { it.timestamp }
                callBack(sortedPosts)
            }
            .addOnFailureListener { exception ->
                callBack(emptyList())
            }
    }
    fun getUserPosts(id: String, callBack: (List<Post>) -> Unit) {
        val db = FirebaseFirestore.getInstance()

        db.collection("Posts")
            .whereEqualTo("sender", id)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val posts = querySnapshot.documents.mapNotNull { document ->
                    val postId = document.id
                    val sender = document.getString("sender") ?: ""
                    val images = document.get("images") as? List<String> ?: emptyList()
                    val username = document.getString("username") ?: ""
                    val profilePhoto = document.getString("senderPhoto") ?: ""
                    val timestamp = document.getLong("timestamp") ?: 0

                    Post(postId,sender,images,profilePhoto,username,timestamp)
                }
                val sortedPosts = posts.sortedByDescending { it.timestamp }
                callBack(sortedPosts)
            }
            .addOnFailureListener { exception ->
                callBack(emptyList())
                exception.printStackTrace()
            }
    }
    fun uploadUserPost(post: Post) {
        val postCollection = db.collection("Posts")
        val postByUser = mapOf(
            "images" to post.images,
            "sender" to post.senderId,
            "senderPhoto" to post.senderPhoto,
            "username" to post.senderUsername,
            "timestamp" to post.timestamp
        )

        postCollection.add(postByUser)
            .addOnSuccessListener{
                Log.d("uploadTask", "your upload task has been completed")
            }
    }

    fun uploadPhotoStorage(userId: String, uris: List<Uri>, callBack: (List<String>) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val urlList = mutableListOf<String>()
        val uploadTasks = mutableListOf<Task<Uri>>()

        uris.forEach { uri ->
            val uuid = UUID.randomUUID().toString()
            val photoRef = storage.reference.child("/users/posts/$userId/$uuid")

            val uploadTask = photoRef.putFile(uri).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                // Continue with the download URL task
                photoRef.downloadUrl
            }.addOnSuccessListener { downloadUrl ->
                urlList.add(downloadUrl.toString())
            }.addOnFailureListener {

            }

            uploadTasks.add(uploadTask)
        }

        Tasks.whenAllComplete(uploadTasks).addOnCompleteListener {
            callBack(urlList)
        }
    }

}