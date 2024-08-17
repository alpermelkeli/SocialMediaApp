package com.alpermelkeli.socialmediaapp

import android.app.Application
import com.alpermelkeli.socialmediaapp.repository.PostsRepository
import com.alpermelkeli.socialmediaapp.repository.UserRepository
import com.alpermelkeli.socialmediaapp.viewmodel.PostsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.UserViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.auth.User


class SocialMediaApplication: Application() {

    lateinit var userViewModel: UserViewModel
    lateinit var postsViewModel: PostsViewModel

    private lateinit var userRepository: UserRepository
    private lateinit var postRepository: PostsRepository

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        initialize()
    }

    private fun initialize() {
        userRepository = UserRepository()
        postRepository = PostsRepository(userRepository)
        userViewModel = UserViewModel(application = this, userRepository)
        postsViewModel = PostsViewModel(application = this, postRepository)
    }
}