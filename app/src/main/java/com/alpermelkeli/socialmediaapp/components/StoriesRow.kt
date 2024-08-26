package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.model.Story

@Composable
fun StoriesRow(addCollection:Boolean, size:Dp, profilePhoto:String, stories:List<Story>, scrollState: LazyListState, onClickedAddCollection:()->Unit, onClickedStory:(Story)->Unit){


    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .height(size)
        .background(MaterialTheme.colorScheme.background),
        state = scrollState) {
        item {
            if(addCollection){
                AddCollectionItem(size = size, profilePhoto = profilePhoto){

                    onClickedAddCollection()
                }
            }
        }
        items(stories){


            StoriesRowItem(size,false,it){
                onClickedStory(it)
            }
        }

    }

}
@Composable
fun AddCollectionItem(size:Dp, profilePhoto: String ,onClickedAddCollection: () -> Unit){
    Column(modifier = Modifier
        .size(size = size)
        .clickable { onClickedAddCollection() },
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier.size(size.times(0.7f)),
            contentAlignment = Alignment.Center) {
            Image(imageVector = ImageVector.vectorResource(id = R.drawable.opened_story_oval), contentDescription = "story",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(size.times(0.7f)))

            ShimmerEffectImage(modifier = Modifier
                .clip(CircleShape)
                .size(size.times(0.6f)),
                    data = profilePhoto)

        }

        Spacer(modifier = Modifier.height(5.dp))

        Text(text = "Add",
            color = MaterialTheme.colorScheme.secondary)

    }

}

