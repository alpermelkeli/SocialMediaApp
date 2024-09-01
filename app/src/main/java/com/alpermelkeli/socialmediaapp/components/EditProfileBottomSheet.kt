package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileBottomSheet(sheetState: SheetState, onDismissRequest:()->Unit){
    val scope = rememberCoroutineScope()

    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val userViewModel = context.userViewModel

    val user by userViewModel.user.observeAsState()

    val galleryViewModel = context.galleryViewModel

    val photos by galleryViewModel.photos.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        galleryViewModel.getPhotos()
    }

    val selectedProfilePhoto by galleryViewModel.selectedProfilePhoto.observeAsState()

    ModalBottomSheet(onDismissRequest = {
        onDismissRequest()
        scope.launch { sheetState.hide() } }, sheetState = sheetState) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {

            item {
                ShimmerEffectImage(modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape), data = selectedProfilePhoto?.uri.toString())

                Button(onClick = { selectedProfilePhoto?.uri?.let {
                    userViewModel.updateProfilePhoto(user?.id.toString(), it)
                } }) {
                    Text(text = "Upload photo")
                }
            }

            items(photos.chunked(3)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    rowItems.forEach { galleryPhoto ->
                        GalleryPhotoCard(galleryPhoto, Modifier
                            .weight(1f)
                            .padding(0.5.dp)
                            .height(120.dp)){
                            galleryViewModel.selectProfilePhoto(it)
                        }
                    }

                }
            }

        }

    }



}