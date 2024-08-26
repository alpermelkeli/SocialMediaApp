package com.alpermelkeli.socialmediaapp.repository

import android.content.Context
import android.provider.MediaStore
import com.alpermelkeli.socialmediaapp.model.GalleryPhoto

class GalleryRepository(private val context: Context) {

    fun getPhotosFromGallery(callback: (List<GalleryPhoto>) -> Unit) {
        val photos = mutableListOf<GalleryPhoto>()
        val contentResolver = context.contentResolver
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val photoUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon()
                    .appendPath(id.toString())
                    .build()
                photos.add(GalleryPhoto(photoUri, false))
            }
        }
        callback(photos.reversed())
    }
}
