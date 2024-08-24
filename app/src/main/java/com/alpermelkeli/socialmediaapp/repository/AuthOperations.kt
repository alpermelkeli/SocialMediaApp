package com.alpermelkeli.socialmediaapp.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

object AuthOperations{

    private val auth = FirebaseAuth.getInstance()

    private val db = FirebaseFirestore.getInstance()

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

    fun register(email: String, password: String, username: String, callBack: (AuthResults) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val userCollection = db.collection("Users")
                val user = mapOf(
                    "username" to username,
                    "profilePhoto" to "",
                    "about" to "",
                    "followers" to emptyList<String>(),
                    "following" to emptyList<String>()
                )
                val uid = it.result.user?.uid

                if (uid != null) {
                    userCollection.document(uid).set(user)
                        .addOnSuccessListener {
                            callBack(AuthResults.Success)
                        }
                        .addOnFailureListener {
                            callBack(AuthResults.Failure)
                        }
                } else {
                    callBack(AuthResults.Failure)
                }
            } else {
                callBack(AuthResults.Failure)
            }
        }.addOnFailureListener {
            callBack(AuthResults.Failure)
        }
    }




}