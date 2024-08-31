package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alpermelkeli.socialmediaapp.model.GalleryPhoto
import com.alpermelkeli.socialmediaapp.repository.GalleryRepository

class GalleryViewModel(application: Application, private val galleryRepository: GalleryRepository) : AndroidViewModel(application) {
    private val _photos: MutableLiveData<List<GalleryPhoto>> = MutableLiveData()
    val photos: LiveData<List<GalleryPhoto>> get() = _photos

    private val _selectedPhotos: MutableLiveData<List<GalleryPhoto>> = MutableLiveData()
    val selectedPhotos: LiveData<List<GalleryPhoto>> get() = _selectedPhotos

    private val _selectedProfilePhoto : MutableLiveData<GalleryPhoto> = MutableLiveData()
    val selectedProfilePhoto : LiveData<GalleryPhoto> get() = _selectedProfilePhoto

    init {
        _selectedPhotos.value = emptyList()
    }

    fun getPhotos() {
        galleryRepository.getPhotosFromGallery {
            _photos.postValue(it)
        }
    }

    fun selectPhoto(photo: GalleryPhoto) {
        val updatedList = _photos.value?.map {
            if (it == photo) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        } ?: emptyList()
        _photos.postValue(updatedList)
        _selectedPhotos.postValue(updatedList.filter { it.isSelected })
    }

    fun selectProfilePhoto(photo:GalleryPhoto){
        _selectedProfilePhoto.postValue(photo)
    }
}
