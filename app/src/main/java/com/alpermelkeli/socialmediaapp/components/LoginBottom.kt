package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpermelkeli.socialmediaapp.ui.theme.Black50
import com.alpermelkeli.socialmediaapp.ui.theme.Grey50

@Composable
fun LoginBottom(onSignUpClicked:()->Unit){
    Column(
        Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalArrangement = Arrangement.Center,
        ) {

        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Grey50))

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            Text(text = "Don't have an account?",
                color = Grey50,
                fontSize = 12.sp)

            Spacer(modifier = Modifier.width(10.dp))

            Text(text = "Sign up.",
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.clickable { onSignUpClicked() }
                )

        }
    }



}