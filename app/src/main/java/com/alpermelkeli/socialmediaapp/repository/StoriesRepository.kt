package com.alpermelkeli.socialmediaapp.repository

import android.net.Uri
import android.util.Log
import com.alpermelkeli.socialmediaapp.model.Story
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class StoriesRepository {
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getHomePageStories(following:List<String>, callBack:(List<Story>) -> Unit){
        if (following.isEmpty()) {
            callBack(emptyList())
            return
        }
        db.collection("Stories")
            .whereIn("senderId", following)
            .get()
            .addOnSuccessListener {querySnapshot->
                val stories = querySnapshot.documents.mapNotNull {document->
                    val storyId = document.id
                    val senderId = document.getString("senderId") ?: ""
                    val senderUsername = document.getString("username") ?: ""
                    val senderProfilePhoto = document.getString("profilePhoto") ?: ""
                    val image = document.getString("image") ?: ""
                    val timestamp = document.getLong("timestamp") ?: 0
                    Story(storyId,senderId,senderProfilePhoto, senderUsername, image,timestamp)
                }
                val sortedStories = stories.sortedByDescending { it.timestamp }
                callBack(sortedStories)
            }
            .addOnFailureListener {
                callBack(emptyList())
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
            // After the file is uploaded, get the download URL
            photoRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                callBack(downloadUrl.toString())
            }.addOnFailureListener {
                // Handle failure in retrieving download URL
                // Log or show an error message
            }
        }.addOnFailureListener {
            // Handle the file upload failure
            // Log or show an error message
        }
    }

}

