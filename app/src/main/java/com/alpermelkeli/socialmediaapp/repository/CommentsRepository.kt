package com.alpermelkeli.socialmediaapp.repository

import android.util.Log
import com.alpermelkeli.socialmediaapp.model.Comment
import com.google.firebase.firestore.FirebaseFirestore

class CommentsRepository {
    private val db = FirebaseFirestore.getInstance()

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