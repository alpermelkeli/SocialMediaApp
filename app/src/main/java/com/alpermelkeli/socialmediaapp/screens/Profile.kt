package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.R
import com.alpermelkeli.socialmediaapp.components.ProfileTopBar
import com.alpermelkeli.socialmediaapp.repository.AuthOperations

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Profile(onClickedLogOut:()->Unit){
    Scaffold(Modifier.fillMaxSize(),
        topBar = {ProfileTopBar(onMenuClicked = {}
        )}) {
        Box(Modifier.fillMaxSize().padding(start = 10.dp, top = 90.dp),
            contentAlignment = Alignment.TopStart) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier.clip(CircleShape)
                    .size(90.dp)
            )
        Column(
            Modifier
                .fillMaxWidth()
                .height(220.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {




        }

        }
    }
}

@Preview
@Composable
fun ProfilePreview(){
    Profile {

    }
}
/*Button(onClick = {AuthOperations.logOut()
                    onClickedLogOut()
}) {


}
 */