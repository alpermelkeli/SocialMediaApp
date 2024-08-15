package com.alpermelkeli.socialmediaapp.repository

sealed class AuthResults() {
    object Success : AuthResults()
    object Failure : AuthResults()
}
