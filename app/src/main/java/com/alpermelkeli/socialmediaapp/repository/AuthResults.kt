package com.alpermelkeli.socialmediaapp.repository

sealed class AuthResults(val message:String) {
    object Success : AuthResults("Logged in successfully")
    object Failure : AuthResults("There was a failure")
}
