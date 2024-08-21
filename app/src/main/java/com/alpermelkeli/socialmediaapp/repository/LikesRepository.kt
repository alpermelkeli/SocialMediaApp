package com.alpermelkeli.socialmediaapp.repository

import android.util.Log
import com.alpermelkeli.socialmediaapp.model.Like
import com.google.firebase.firestore.FirebaseFirestore

class LikesRepository {
    private val db = FirebaseFirestore.getInstance()

    fun sendLike(like: Like) {
        val likeCollection = db.collection("Likes")
        val likeDetails = mapOf(
            "userId" to like.userId,
            "postId" to like.postId,
            "createdAt" to like.createdAt,
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
                    val likeId = document.id
                    val userId = document.getString("userId") ?: ""
                    val createdAt = document.getLong("createdAt") ?: 0
                    Like(likeId,postId, userId, createdAt)
                }
                callBack(postLikesDetails)
            }
            .addOnFailureListener {
                callBack(emptyList())
            }
    }
    fun removeLike(likeId: String) {
        db.collection("Likes")
            .document(likeId)
            .delete()
            .addOnSuccessListener {
                Log.d("Likes", "Like successfully removed from the database")
            }
            .addOnFailureListener { e ->
                Log.e("Likes", "Error removing like", e)
            }
    }


}