package com.alpermelkeli.socialmediaapp.repository

import android.util.Log
import com.alpermelkeli.socialmediaapp.model.Post
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking

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
                    val comments = document.get("comments") as? List<String> ?: emptyList()
                    val id = document.id
                    val images = document.get("images") as? List<String> ?: emptyList()
                    val likeCount = document.getLong("likeCount")?.toInt() ?: 100
                    val username = document.getString("username") ?: ""
                    val profilePhoto = document.getString("senderPhoto") ?: ""
                    Post(id,comments,likeCount,images,profilePhoto,username)
                }
                callBack(posts)
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
                    val comments = document.get("comments") as? List<String> ?: emptyList()
                    val id = document.id
                    val images = document.get("images") as? List<String> ?: emptyList()
                    val likeCount = document.getLong("likeCount")?.toInt() ?: 100
                    val username = document.getString("username") ?: ""
                    val profilePhoto = document.getString("senderPhoto") ?: ""
                    Post(id,comments,likeCount,images,profilePhoto,username)
                }
                callBack(posts)
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
            "sender" to post.id,
            "senderPhoto" to post.senderPhoto,
            "username" to post.senderUsername,
            "comments" to post.comments,
            "likeCount" to post.likeCount
        )

        postCollection.add(postByUser)
            .addOnSuccessListener{
                Log.d("uploadTask", "your upload task has been completed")
            }
    }

}