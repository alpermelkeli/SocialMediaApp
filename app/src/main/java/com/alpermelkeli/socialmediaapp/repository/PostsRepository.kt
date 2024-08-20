package com.alpermelkeli.socialmediaapp.repository

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.internal.composableLambdaInstance
import com.alpermelkeli.socialmediaapp.model.Comment
import com.alpermelkeli.socialmediaapp.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.Collections
import java.util.UUID

class PostsRepository {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
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
                    val likeCount = document.getLong("likeCount")?.toInt() ?: 100
                    val username = document.getString("username") ?: ""
                    val profilePhoto = document.getString("senderPhoto") ?: ""
                    val timestamp = document.getLong("timestamp") ?: 0
                    Post(postId,sender,likeCount,images,profilePhoto,username,timestamp)
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
                    val likeCount = document.getLong("likeCount")?.toInt() ?: 100
                    val username = document.getString("username") ?: ""
                    val profilePhoto = document.getString("senderPhoto") ?: ""
                    val timestamp = document.getLong("timestamp") ?: 0

                    Post(postId,sender,likeCount,images,profilePhoto,username,timestamp)
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
            "likeCount" to post.likeCount,
            "timestamp" to post.timestamp
        )

        postCollection.add(postByUser)
            .addOnSuccessListener{
                Log.d("uploadTask", "your upload task has been completed")
            }
    }

    fun uploadPhotoStorage(userId:String,uri:Uri,callBack: (String) -> Unit){
         val uuid = UUID.randomUUID()
         val photoRef = storage.reference.child("/users/posts/${userId}/${uuid}")
        photoRef.putFile(uri).addOnSuccessListener{
            val uploadedRef = storage.reference.child("/users/posts/${userId}/${uuid}")
            uploadedRef.downloadUrl.addOnSuccessListener {
                callBack(it.toString())
            }
        }.addOnFailureListener {
            //error
        }
    }

    fun sendComment(postId:String, comment: Comment){
        val commentsCollection = db.collection("Comments")
        val commentByUser = mapOf(
            "content" to comment.content,
            "createdAt" to comment.createdAt,
            "postId" to postId,
            "sender" to comment.senderId,
            "senderPhoto" to comment.senderPhoto,
            "senderUsername" to comment.senderUsername
        )
        commentsCollection.add(commentByUser)
            .addOnSuccessListener {
                Log.d("commentTask","successfully")
            }

    }

    fun getPostComments(postId:String,callBack: (List<Comment>) -> Unit){
        db.collection("Comments")
            .whereEqualTo("postId",postId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val comments = querySnapshot.documents.mapNotNull { document ->
                    val sender = document.getString("sender") ?: ""
                    val senderPhoto = document.getString("senderPhoto") ?: ""
                    val senderUsername = document.getString("senderUsername") ?: ""
                    val createdAt = document.getLong("createdAt") ?: 0
                    val content = document.getString("content") ?: ""
                    Comment(sender,senderPhoto,senderUsername,content,createdAt)
                }
                callBack(comments)
            }
            .addOnFailureListener {
                callBack(emptyList())
            }
    }

}