package com.alpermelkeli.socialmediaapp.repository

import com.alpermelkeli.socialmediaapp.model.ChatMessage
import com.alpermelkeli.socialmediaapp.model.Conversation
import com.alpermelkeli.socialmediaapp.model.MessageStatus
import com.alpermelkeli.socialmediaapp.model.MessageType
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.UUID

class MessageRepository(private val userRepository: UserRepository) {
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getConversations(callback:(List<Conversation>)->Unit){
        val id = AuthOperations.getUser()?.uid
        id?.let {
            db.collection("Conversations")
                .orderBy("lastUpdatedAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val conversations = querySnapshot.documents.mapNotNull { document ->

                            val conversationId = document.id

                            val lastUpdatedAt = document.getTimestamp("lastUpdatedAt") ?: Timestamp.now()

                            val lastMessage = document.getString("lastMessage") ?: ""

                            val participants = document.get("participants") as? List<String> ?: emptyList()

                            val otherUserId = participants.filter { it!=id }[0]

                            if (!participants.any { it == id }) {
                                return@mapNotNull null
                            }

                            val otherUser = userRepository.getUserDocument(otherUserId)

                            otherUser?.let {
                                Conversation(conversationId,it.id,it.profilePhoto,it.username,lastMessage,lastUpdatedAt)
                            }
                        }
                        withContext(Dispatchers.Main) {
                            callback(conversations)
                        }
                    }
                }
                .addOnFailureListener {


                }
        }
    }
    fun getMessages(conversationId: String, callback: (List<ChatMessage>) -> Unit) {
        val id = AuthOperations.getUser()?.uid
        id?.let {
            db.collection("Conversations")
                .document(conversationId)
                .collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener { querySnapshot, e ->
                    if (e != null) {
                        callback(emptyList())
                        return@addSnapshotListener
                    }

                    querySnapshot?.let {
                        CoroutineScope(Dispatchers.IO).launch {
                            val chatMessages = querySnapshot.documents.mapNotNull { document ->
                                val content = document.getString("content") ?: ""
                                val messageType = when (document.getString("messageType")) {
                                    "text" -> MessageType.TEXT
                                    "image" -> MessageType.IMAGE
                                    "voice" -> MessageType.VOICE
                                    else -> MessageType.TEXT
                                }
                                val recipientId = document.getString("recipientId") ?: ""
                                val senderId = document.getString("senderId") ?: ""
                                val status = when (document.getString("status")) {
                                    "sent" -> MessageStatus.SENT
                                    "read" -> MessageStatus.READ
                                    else -> MessageStatus.READ
                                }

                                val timestamp = document.getTimestamp("timestamp") ?: Timestamp.now()

                                ChatMessage(
                                    isReceived = id == recipientId,
                                    status = status,
                                    content = content,
                                    isEmojiOnly = false,
                                    senderId = senderId,
                                    type = messageType,
                                    timestamp = timestamp
                                )
                            }

                            withContext(Dispatchers.Main) {
                                callback(chatMessages)
                            }
                        }
                    }
                }
        }
    }

    fun sendMessage(conversationId: String, content: String, callback: (Boolean) -> Unit) {
        val senderId = AuthOperations.getUser()?.uid
        senderId?.let {
            CoroutineScope(Dispatchers.IO).launch {
                val conversationSnapshot = db.collection("Conversations")
                    .document(conversationId)
                    .get()
                    .await()

                val participants = conversationSnapshot.get("participants") as? List<String> ?: emptyList()

                val recipientId = participants.firstOrNull { it != senderId }

                recipientId?.let {
                    val message = mapOf(
                        "content" to content,
                        "messageType" to "text",
                        "recipientId" to recipientId,
                        "senderId" to senderId,
                        "status" to "sent",
                        "timestamp" to Timestamp.now()
                    )

                    db.collection("Conversations")
                        .document(conversationId)
                        .collection("messages")
                        .add(message)
                        .addOnSuccessListener {
                            db.collection("Conversations")
                                .document(conversationId)
                                .update(
                                    mapOf(
                                        "lastMessage" to content,
                                        "lastUpdatedAt" to Timestamp.now()
                                    )
                                )
                                .addOnSuccessListener {
                                    callback(true)
                                }
                        }
                        .addOnFailureListener {
                            callback(false)
                        }
                }
            }
        }
    }
    fun startConversation(otherUserId:String, callback: (conversationId: String) -> Unit){
        val starterId = AuthOperations.getUser()?.uid
        starterId?.let {
            val conversation = mapOf(
                "conversationId" to UUID.randomUUID().toString(),
                "initiatedAt" to Timestamp.now(),
                "initiatedBy" to it,
                "lastMessage" to "",
                "lastUpdatedAt" to Timestamp.now(),
                "participants" to listOf(it,otherUserId)
            )
            db.collection("Conversations")
                .document(conversation["conversationId"].toString())
                .set(conversation)
                .addOnSuccessListener {
                    callback(conversation["conversationId"].toString())
                }
                .addOnFailureListener {
                    callback("false")
                }



        }

    }


}