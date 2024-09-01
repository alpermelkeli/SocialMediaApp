package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.components.DefaultSearchBar
import com.alpermelkeli.socialmediaapp.components.MessageTopBar
import com.alpermelkeli.socialmediaapp.components.LastMessageItem
import com.alpermelkeli.socialmediaapp.model.LastMessage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Messages(){
    var searchText by remember{ mutableStateOf("") }
    val lastMessage = LastMessage("alpermelkeli", "profile", "This is my last message!")
    val lastMessages = mutableListOf<LastMessage>()
    for (i in 1..100){
        lastMessages.add(lastMessage)
    }


    Scaffold(topBar = {MessageTopBar(onClickBack = {}, onClickAddMessage = {})}) {

        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(top = it.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally
            ){

            item {
                DefaultSearchBar(searchText = searchText) {
                    searchText = it
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(lastMessages){
                LastMessageItem(lastMessage = it)
            }




        }

    }
}


