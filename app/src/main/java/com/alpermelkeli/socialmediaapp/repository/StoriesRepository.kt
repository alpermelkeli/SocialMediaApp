package com.alpermelkeli.socialmediaapp.repository

import android.net.Uri
import android.util.Log
import com.alpermelkeli.socialmediaapp.model.Story
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class StoriesRepository(private val userRepository: UserRepository) {
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getHomePageStories(following: List<String>, callBack: (List<Pair<String, List<Story>>>) -> Unit) {
        if (following.isEmpty()) {
            callBack(emptyList())
            return
        }
        val oneDayInMillis = 86400000
        val currentTime = System.currentTimeMillis()
        val oneDayAgo = currentTime-oneDayInMillis
        db.collection("Stories")
            .whereIn("senderId", following)
            .get()
            .addOnSuccessListener { querySnapshot ->
                CoroutineScope(Dispatchers.IO).launch {
                    val stories = querySnapshot.documents.mapNotNull { document ->
                        val storyId = document.id
                        val senderId = document.getString("senderId") ?: return@mapNotNull null
                        val image = document.getString("image") ?: ""
                        val createdAt = document.getLong("createdAt") ?: 0
                        val user = userRepository.getUserDocument(senderId)
                        user?.let {
                            Story(storyId, senderId, it.profilePhoto, it.username, image, createdAt)
                        }
                    }

                    withContext(Dispatchers.Main){
                        val groupedStories = stories.sortedBy { it.createdAt }.groupBy { it.senderId }
                        val sortedGroupedStories = groupedStories.entries
                            .map { (senderId, storyList) ->
                                Pair(senderId, storyList)
                            }
                            .sortedByDescending { (_, stories) ->
                                stories.maxOfOrNull { it.createdAt } ?: 0
                            }
                        callBack(sortedGroupedStories)
                    }
                }


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
            "createdAt" to story.createdAt
        )

        postCollection.add(storyByUser)
            .addOnSuccessListener{
                Log.d("uploadTask", "your upload task has been completed")
            }
    }

    fun uploadStoryStorage(userId: String, uri: Uri, callBack: (String) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val uuid = UUID.randomUUID().toString()
        val photoRef = storage.reference.child("/Users/$userId/stories/$uuid")

        photoRef.putFile(uri).addOnSuccessListener {
            photoRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                callBack(downloadUrl.toString())
            }.addOnFailureListener {
            }
        }.addOnFailureListener {
        }
    }

}

