package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.model.Story


@Composable
fun StoriesRowItem(size: Dp, isOpened:Boolean, story: Story, onClickedStory: (Story) -> Unit){
    Column(modifier = Modifier
        .size(size = size)
        .clickable { onClickedStory(story) },
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier.size(size.times(0.7f)),
            contentAlignment = Alignment.Center) {

            Image(imageVector = ImageVector.vectorResource(id = if (isOpened) R.drawable.opened_story_oval else R.drawable.story_oval), contentDescription = "story",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(size.times(0.7f)))

            ShimmerEffectImage(modifier = Modifier
                .clip(CircleShape)
                .size(size.times(0.6f)), data = story.profilePhoto)

        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(text = story.username,
            color = MaterialTheme.colorScheme.secondary)

    }

}