package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.BackTopBar
import com.alpermelkeli.socialmediaapp.components.DefaultButton
import com.alpermelkeli.socialmediaapp.components.DefaultTextField
import com.alpermelkeli.socialmediaapp.repository.AuthOperations
import com.alpermelkeli.socialmediaapp.repository.AuthResults
import com.alpermelkeli.socialmediaapp.ui.theme.Blue50
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginWithField(emailText:String,onClickBack:()->Unit,onClickLogin:()->Unit, onClickForgotPassword:()->Unit, onClickSignUp:()->Unit){
    val darkTheme = isSystemInDarkTheme()

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val appContext = context.applicationContext as SocialMediaApplication

    val storeData = appContext.storeData

    var usernameText by remember {
        mutableStateOf(emailText)
    }
    var passwordText by remember {
        mutableStateOf("")
    }
    val isButtonEnabled by remember {
        derivedStateOf {
            usernameText.isNotEmpty()&&passwordText.isNotEmpty()
        }
    }

    SocialMediaAppTheme {


        Scaffold(Modifier.fillMaxSize(), topBar = {
            BackTopBar{onClickBack()} }) {

            Column(Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Image(imageVector = ImageVector.vectorResource(id = if(darkTheme) R.drawable.instagram_logo_white else R.drawable.instagram_logo_black), contentDescription = "logo")

                Spacer(modifier = Modifier.height(50.dp))

                DefaultTextField("Username", usernameText, visibility = true){
                    usernameText = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                DefaultTextField("Password",passwordText, visibility = false){
                    passwordText = it
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Forgot password?", modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(20.dp)
                    .clickable { onClickForgotPassword() },
                    textAlign = TextAlign.End,
                    fontSize = 12.sp,
                    color = Blue50
                    )

                Spacer(modifier = Modifier.height(30.dp))

                DefaultButton(modifier = Modifier.fillMaxWidth(0.9f), "Log in",isEnabled = isButtonEnabled) {
                    AuthOperations.login(usernameText, passwordText) { authResult ->
                        if (authResult == AuthResults.Success) {
                            Toast.makeText(context,authResult.message,Toast.LENGTH_SHORT).show()
                            scope.launch {
                                storeData.saveEmail(usernameText)
                            }
                            onClickLogin()
                        } else {
                            Toast.makeText(context,authResult.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier
                        .height(1.dp)
                        .background(Color.LightGray)
                        .width(150.dp))
                    Text(text = "OR", fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp)
                    Spacer(modifier = Modifier
                        .width(150.dp)
                        .height(1.dp)
                        .background(Color.LightGray))
                }
                
                Spacer(modifier = Modifier.height(30.dp))

                Row(Modifier.fillMaxWidth(0.9f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Don't have an account",
                        color = Color.LightGray,
                        fontSize = 12.sp)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = "Sign up.",
                        color = Blue50,
                        fontSize = 12.sp,
                        modifier = Modifier.clickable {onClickSignUp()})

                }
            }
        }
    }
}