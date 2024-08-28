package com.alpermelkeli.socialmediaapp.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alpermelkeli.socialmediaapp.model.Story
import com.alpermelkeli.socialmediaapp.repository.StoriesRepository
import kotlinx.coroutines.launch

class StoriesViewModel(application: Application, private val storiesRepository: StoriesRepository) : AndroidViewModel(application) {

    private val _homePageStories : MutableLiveData<List<Pair<String, List<Story>>>> = MutableLiveData()

    val homePageStories : LiveData<List<Pair<String, List<Story>>>> get() = _homePageStories

    fun getHomePageStories(following: List<String>) {
        viewModelScope.launch {
            storiesRepository.getHomePageStories(following) { storiesMap ->
                _homePageStories.postValue(storiesMap)
            }
        }
    }

    fun uploadStory(story: Story, uri: Uri) {
        viewModelScope.launch {
            storiesRepository.uploadStoryStorage(story.senderId, uri) { imageUrl ->
                val updatedStory = story.copy(image = imageUrl)
                storiesRepository.uploadUserStory(updatedStory)
            }
        }
    }

}
