package com.alpermelkeli.socialmediaapp.repository

import android.util.Log
import com.alpermelkeli.socialmediaapp.model.Like
import com.google.firebase.firestore.FirebaseFirestore

class LikesRepository {
    private val db = FirebaseFirestore.getInstance()

    fun updateLikeData(post: String, likes: Like) {
        val likeCollection = db.collection("Likes")
        val likeDetails = mapOf(
            "userId" to likes.userId,
            "postId" to likes.postId,
            "createdAt" to likes.createdAt,
        )
        likeCollection.add(likeDetails)
            .addOnSuccessListener {
                Log.d("Likes", "your like has been submitted in db")
            }
    }

    fun getPostLikes(postId: String, callBack: (List<Like>) -> Unit) {
        db.collection("Likes")
            .whereEqualTo("postId", postId)
            .get()
            .addOnSuccessListener { result ->
                val postLikesDetails = result.documents.mapNotNull { document ->
                    val userId = document.getString("userId") ?: ""
                    val createdAt = document.getLong("createdAt") ?: 0
                    Like(userId, postId, createdAt)
                }
                callBack(postLikesDetails)
            }
            .addOnFailureListener {
                callBack(emptyList())
            }
    }


}