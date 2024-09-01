package com.alpermelkeli.socialmediaapp.repository

import android.util.Log
import com.alpermelkeli.socialmediaapp.model.Comment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsRepository(private val userRepository: UserRepository) {
    private val db = FirebaseFirestore.getInstance()

    fun sendComment(postId:String, comment: Comment){
        val commentsCollection = db.collection("Comments")
        val commentByUser = mapOf(
            "content" to comment.content,
            "createdAt" to comment.createdAt,
            "postId" to postId,
            "sender" to comment.senderId,
        )
        commentsCollection.add(commentByUser)
            .addOnSuccessListener {
                Log.d("commentTask","successfully")
            }

    }

    fun getPostComments(postId: String, callBack: (List<Comment>) -> Unit) {
        db.collection("Comments")
            .whereEqualTo("postId", postId)
            .get()
            .addOnSuccessListener { querySnapshot ->
                CoroutineScope(Dispatchers.IO).launch {
                    val comments = querySnapshot.documents.mapNotNull { document ->
                        val createdAt = document.getLong("createdAt") ?: 0
                        val content = document.getString("content") ?: ""
                        val sender = document.getString("sender") ?: ""

                        val user = userRepository.getUserDocument(sender)
                        user?.let {
                            Comment(it.id, it.profilePhoto, it.username, content, createdAt)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        callBack(comments)
                    }
                }
            }
            .addOnFailureListener {
                callBack(emptyList())
            }
    }


}
