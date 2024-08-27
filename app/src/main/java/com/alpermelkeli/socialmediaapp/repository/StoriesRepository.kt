package com.alpermelkeli.socialmediaapp.repository

import android.net.Uri
import android.util.Log
import com.alpermelkeli.socialmediaapp.model.Story
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.runBlocking
import java.util.UUID

class StoriesRepository {
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getHomePageStories(following: List<String>, callBack: (List<Pair<String, List<Story>>>) -> Unit) {
        if (following.isEmpty()) {
            callBack(emptyList())
            return
        }

        db.collection("Stories")
            .whereIn("senderId", following)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Map documents to Story objects
                val stories = querySnapshot.documents.mapNotNull { document ->
                    val storyId = document.id
                    val senderId = document.getString("senderId") ?: return@mapNotNull null
                    val senderUsername = document.getString("username") ?: ""
                    val senderProfilePhoto = document.getString("profilePhoto") ?: ""
                    val image = document.getString("image") ?: ""
                    val timestamp = document.getLong("timestamp") ?: 0
                    Story(storyId, senderId, senderProfilePhoto, senderUsername, image, timestamp)
                }

                // Group stories by senderId
                val groupedStories = stories.groupBy { it.senderId }

                // Convert grouped stories to a list of pairs
                val result = groupedStories.map { Pair(it.key, it.value) }

                callBack(result)
            }
            .addOnFailureListener {
                callBack(emptyList()) // Return an empty list on failure
            }
    }




    fun uploadUserStory(story: Story) {
        val postCollection = db.collection("Stories")
        val storyByUser = mapOf(
            "image" to story.image,
            "senderId" to story.senderId,
            "profilePhoto" to story.profilePhoto,
            "username" to story.username,
            "timestamp" to story.timestamp
        )

        postCollection.add(storyByUser)
            .addOnSuccessListener{
                Log.d("uploadTask", "your upload task has been completed")
            }
    }

    fun uploadStoryStorage(userId: String, uri: Uri, callBack: (String) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val uuid = UUID.randomUUID().toString()
        val photoRef = storage.reference.child("/users/stories/$userId/$uuid")

        photoRef.putFile(uri).addOnSuccessListener {
            photoRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                callBack(downloadUrl.toString())
            }.addOnFailureListener {
            }
        }.addOnFailureListener {
        }
    }

}

