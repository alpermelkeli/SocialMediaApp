package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.alpermelkeli.socialmediaapp.model.Permission

class PermissionViewModel(application: Application) : AndroidViewModel(application) {

    fun checkCameraPermission(context: Context, onGranted: () -> Unit, onUnGranted:()->Unit) {
        val cameraPermission = Permission.CameraPermission
        if (cameraPermission.hasPermission(context)) {
            onGranted()
        }
        else{
            onUnGranted()
        }
    }
    fun checkStoragePermission(context: Context, onGranted: () -> Unit, onUnGranted: () -> Unit){
        val storagePermission = Permission.MediaImagesPermission
        if(storagePermission.hasPermission(context)){
            onGranted()
        }
        else{
            onUnGranted()
        }
    }
}
