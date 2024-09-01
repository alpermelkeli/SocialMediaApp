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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections
import java.util.UUID

class PostsRepository(private val userRepository: UserRepository) {
    private val db = FirebaseFirestore.getInstance()

    fun getUserHomePagePosts(followings: List<String>, callBack: (List<Post>) -> Unit) {
        if (followings.isEmpty()) {
            callBack(emptyList())
            return
        }
        db.collection("Posts")
            .whereIn("sender", followings)
            .get()
            .addOnSuccessListener { querySnapshot ->
                CoroutineScope(Dispatchers.IO).launch {
                    val posts = querySnapshot.documents.mapNotNull { document ->
                        val postId = document.id
                        val sender = document.getString("sender") ?: ""
                        val images = document.get("images") as? List<String> ?: emptyList()
                        val timestamp = document.getLong("timestamp") ?: 0
                        val user = userRepository.getUserDocument(sender)
                        user?.let{
                            Post(postId,sender,images,it.profilePhoto,it.username,timestamp)
                        }
                    }
                    withContext(Dispatchers.Main){
                        val sortedPosts = posts.sortedByDescending { it.timestamp }
                        callBack(sortedPosts)
                    }
                }
            }
            .addOnFailureListener { exception ->
                callBack(emptyList())
                exception.printStackTrace()
            }
    }

    fun getUserPosts(id: String, callBack: (List<Post>) -> Unit) {

        db.collection("Posts")
            .whereEqualTo("sender", id)
            .get()
            .addOnSuccessListener { querySnapshot ->
                CoroutineScope(Dispatchers.IO).launch {
                    val posts = querySnapshot.documents.mapNotNull { document ->
                        val postId = document.id
                        val sender = document.getString("sender") ?: ""
                        val images = document.get("images") as? List<String> ?: emptyList()
                        val timestamp = document.getLong("timestamp") ?: 0
                        val user = userRepository.getUserDocument(sender)
                        user?.let{
                            Post(postId,sender,images,it.profilePhoto,it.username,timestamp)
                        }
                    }
                    withContext(Dispatchers.Main){
                        val sortedPosts = posts.sortedByDescending { it.timestamp }
                        callBack(sortedPosts)
                    }
                }
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
            val photoRef = storage.reference.child("/Users/$userId/posts/$uuid")

            val uploadTask = photoRef.putFile(uri).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
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