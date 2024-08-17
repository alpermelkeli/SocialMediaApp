package com.alpermelkeli.socialmediaapp.repository

import com.alpermelkeli.socialmediaapp.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking

class PostsRepository(private val userRepository: UserRepository) {
    private val db = FirebaseFirestore.getInstance()

    fun getUserHomePagePosts(followings: List<String>, callBack: (List<Post>) -> Unit) {
        if (followings.isEmpty()) {
            callBack(emptyList())
            return
        }

        db.collection("Posts")
            .whereIn("sender", followings) // Filter posts whose sender id is in the followings list
            .get()
            .addOnSuccessListener { querySnapshot ->
                val posts = querySnapshot.documents.mapNotNull { document ->
                    val comments = document.get("comments") as? List<String> ?: emptyList()
                    val id = document.id
                    val sender = document.getString("sender") ?: "Error"
                    val images = document.get("images") as? List<String> ?: emptyList()
                    val likeCount = document.getLong("likeCount")?.toInt() ?: 100

                    val user = runBlocking {
                        userRepository.getUserDocumentAsync(sender)
                    }

                    Post(id,comments,likeCount,images,user!!)

                }
                callBack(posts)
            }
            .addOnFailureListener { exception ->
                callBack(emptyList())
            }
    }





}