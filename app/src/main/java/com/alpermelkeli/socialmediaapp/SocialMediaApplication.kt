package com.alpermelkeli.socialmediaapp

import android.app.Application
import com.alpermelkeli.socialmediaapp.repository.CommentsRepository
import com.alpermelkeli.socialmediaapp.repository.GalleryRepository
import com.alpermelkeli.socialmediaapp.repository.LikesRepository
import com.alpermelkeli.socialmediaapp.repository.MessageRepository
import com.alpermelkeli.socialmediaapp.repository.PostsRepository
import com.alpermelkeli.socialmediaapp.repository.StoreData
import com.alpermelkeli.socialmediaapp.repository.StoriesRepository
import com.alpermelkeli.socialmediaapp.repository.UserRepository
import com.alpermelkeli.socialmediaapp.viewmodel.CommentsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.GalleryViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.LikesViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.MessageViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.PermissionViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.PostsViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.StoriesViewModel
import com.alpermelkeli.socialmediaapp.viewmodel.UserViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.auth.User


class SocialMediaApplication: Application() {

    lateinit var storeData: StoreData
    lateinit var permissionViewModel: PermissionViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var postsViewModel: PostsViewModel
    lateinit var likesViewModel: LikesViewModel
    lateinit var commentsViewModel:CommentsViewModel
    lateinit var galleryViewModel: GalleryViewModel
    lateinit var storiesViewModel: StoriesViewModel
    lateinit var messageViewModel: MessageViewModel
    private lateinit var galleryRepository: GalleryRepository
    private lateinit var userRepository: UserRepository
    private lateinit var postRepository: PostsRepository
    private lateinit var likesRepository: LikesRepository
    private lateinit var commentsRepository: CommentsRepository
    private lateinit var storiesRepository: StoriesRepository
    private lateinit var messageRepository: MessageRepository

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        initialize()
    }

    private fun initialize() {
        userRepository = UserRepository()
        postRepository = PostsRepository(userRepository)
        likesRepository = LikesRepository()
        commentsRepository = CommentsRepository(userRepository)
        galleryRepository = GalleryRepository(this.applicationContext)
        storiesRepository = StoriesRepository(userRepository)
        messageRepository = MessageRepository()
        storeData = StoreData(this)
        permissionViewModel = PermissionViewModel(this)
        userViewModel = UserViewModel(application = this, userRepository)
        postsViewModel = PostsViewModel(application = this, postRepository)
        likesViewModel = LikesViewModel(this, likesRepository)
        commentsViewModel = CommentsViewModel(application = this,commentsRepository)
        galleryViewModel = GalleryViewModel(this, galleryRepository)
        storiesViewModel = StoriesViewModel(this,storiesRepository)
        messageViewModel = MessageViewModel(this,messageRepository)
    }
}