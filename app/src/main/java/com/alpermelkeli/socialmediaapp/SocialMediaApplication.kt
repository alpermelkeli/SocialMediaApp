package com.alpermelkeli.socialmediaapp

import android.app.Application
import com.alpermelkeli.socialmediaapp.repository.CommentsRepository
import com.alpermelkeli.socialmediaapp.repository.LikesRepository
import com.alpermelkeli.socialmediaapp.repository.PostsRepository
import com.alpermelkeli.socialmediaapp.repository.UserRepository
import com.alpermelkeli.socialmediaapp.viewmodel.CommentsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.LikesViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.PostsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.UserViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.auth.User


class SocialMediaApplication: Application() {

    lateinit var userViewModel: UserViewModel
    lateinit var postsViewModel: PostsViewModel
    lateinit var likesViewModel: LikesViewModel
    lateinit var commentsViewModel:CommentsViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var postRepository: PostsRepository
    private lateinit var likesRepository: LikesRepository
    private lateinit var commentsRepository: CommentsRepository

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        initialize()
    }

    private fun initialize() {
        userRepository = UserRepository()
        postRepository = PostsRepository()
        likesRepository = LikesRepository()
        commentsRepository = CommentsRepository()
        userViewModel = UserViewModel(application = this, userRepository)
        postsViewModel = PostsViewModel(application = this, postRepository)
        likesViewModel = LikesViewModel(this, likesRepository)
        commentsViewModel = CommentsViewModel(application = this,commentsRepository)
    }
}