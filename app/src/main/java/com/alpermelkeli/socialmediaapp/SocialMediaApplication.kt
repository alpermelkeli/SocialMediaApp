package com.alpermelkeli.socialmediaapp

import android.app.Application
import com.alpermelkeli.socialmediaapp.viewmodel.PostsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.UserViewModel


class SocialMediaApplication: Application() {

    lateinit var userViewModel: UserViewModel
    lateinit var postsViewModel: PostsViewModel

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        userViewModel = UserViewModel(application = Application())
        postsViewModel = PostsViewModel(application = Application())
    }
}