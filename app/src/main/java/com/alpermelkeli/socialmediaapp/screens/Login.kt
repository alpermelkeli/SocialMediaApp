package com.alpermelkeli.socialmediaapp.screens
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.DefaultButton
import com.alpermelkeli.socialmediaapp.components.AuthBottom
import com.alpermelkeli.socialmediaapp.ui.theme.Blue50
import com.alpermelkeli.socialmediaapp.ui.theme.SocialMediaAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Login(onClickedLogin:(String)->Unit, onClickedSwitchAccounts:()->Unit, onClickedSignUp:()->Unit){
    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val storeData = context.storeData

    val username by storeData.getUsername.collectAsState("username")

    val profilePhoto by storeData.getProfilePhoto.collectAsState(initial = "")

    val email by storeData.getEmail.collectAsState(initial = "")
    SocialMediaAppTheme {
        val darkTheme = isSystemInDarkTheme()

        Scaffold(Modifier.fillMaxSize(), bottomBar = {

            AuthBottom(firstText = "Don't have an account ?", secondText = "Sign Up.",onSignUpClicked = {onClickedSignUp()})

        })
        {

            Column(Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {

                Image(imageVector = ImageVector.vectorResource(id = if(darkTheme) R.drawable.instagram_logo_white else R.drawable.instagram_logo_black), contentDescription = "logo")

                Spacer(modifier = Modifier.height(50.dp))

                Image(painter = rememberAsyncImagePainter(model = profilePhoto.toString()), contentDescription = "user",
                    modifier = Modifier
                        .size(85.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = username.toString(),
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp)

                Spacer(modifier = Modifier.height(10.dp))

                DefaultButton(modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp), text = "Log in",isEnabled = true){
                    onClickedLogin(email.toString())
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(text = "Switch accounts",
                    color = Blue50,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable {  })

            }

        }

    }

}
