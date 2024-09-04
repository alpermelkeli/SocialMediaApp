package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpermelkeli.socialmediaapp.model.Conversation

@Composable
fun LastMessageItem(conversation: Conversation, onConversationClicked:(targetUserId:String, conversationId:String)->Unit){

    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onConversationClicked(conversation.otherUserId, conversation.conversationId) },
        verticalAlignment = Alignment.CenterVertically) {

        ShimmerEffectImage(modifier = Modifier
            .size(56.dp)
            .clip(CircleShape),
            data = conversation.profilePhoto
        )

        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 10.dp)) {

            Text(text = conversation.username,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp)

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = conversation.lastMessage,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 13.sp)

        }


    }


}