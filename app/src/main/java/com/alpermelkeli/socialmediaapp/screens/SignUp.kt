package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.components.AuthBottom
import com.alpermelkeli.socialmediaapp.components.DefaultButton
import com.alpermelkeli.socialmediaapp.components.DefaultTextField
import com.alpermelkeli.socialmediaapp.repository.AuthOperations
import com.alpermelkeli.socialmediaapp.repository.AuthResults
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUp(onRegisteredSuccessfully:()->Unit){
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    var emailText by remember{ mutableStateOf("") }
    var usernameText by remember{ mutableStateOf("") }
    var passwordText by remember{ mutableStateOf("") }
    var confirmPasswordText by remember{ mutableStateOf("") }
    val isButtonEnabled by remember {
        derivedStateOf {
            passwordText == confirmPasswordText
        }
    }

    SocialMediaAppTheme {

        Scaffold(modifier = Modifier.fillMaxSize(),
            bottomBar = { AuthBottom(firstText = "Already have an account?", secondText = "Log in.") {

        }}) {

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,) {
                Spacer(modifier = Modifier.height(150.dp))


                Image(imageVector = ImageVector.vectorResource(id = if(isDark) R.drawable.instagram_logo_white else R.drawable.instagram_logo_black), contentDescription = "logo")

                Spacer(modifier = Modifier.height(50.dp))

                DefaultTextField(placeHolder = "E-mail", value = emailText, visibility = true) {
                    emailText = it
                }
                Spacer(modifier = Modifier.height(40.dp))

                DefaultTextField(placeHolder = "Username", value = usernameText, visibility =true ) {
                    usernameText = it
                }
                Spacer(modifier = Modifier.height(40.dp))

                DefaultTextField(placeHolder = "Password", value = passwordText, visibility = false) {
                    passwordText = it
                }
                Spacer(modifier = Modifier.height(40.dp))

                DefaultTextField(placeHolder = "Confirm password", value = confirmPasswordText, visibility = false) {
                    confirmPasswordText = it
                }

                Spacer(modifier = Modifier.height(40.dp))

                DefaultButton(modifier = Modifier.fillMaxWidth(0.9f), text = "Sign Up", isEnabled = isButtonEnabled) {
                    AuthOperations.register(emailText,passwordText,usernameText){
                        if(it == AuthResults.Success){
                            onRegisteredSuccessfully()
                        }
                        else{
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }



            }


        }


    }

}