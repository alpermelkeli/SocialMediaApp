package com.alpermelkeli.socialmediaapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object AuthOperations{

    private val auth = FirebaseAuth.getInstance()

    fun getUser():FirebaseUser?{
        return auth.currentUser
    }

    fun login(email:String, password:String, callBack:(AuthResults)->Unit){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                callBack(AuthResults.Success)
            }
            else{
                callBack(AuthResults.Failure)
            }

        }.addOnFailureListener {
            callBack(AuthResults.Failure)
        }
    }

    fun logOut(){
        auth.signOut()
    }

    fun register(email:String, password:String,callBack: (AuthResults) -> Unit){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if(it.isSuccessful){
                callBack(AuthResults.Success)
            }
            else{
                callBack(AuthResults.Failure)
            }
        }.addOnFailureListener {
            callBack(AuthResults.Failure)
        }
    }



}