package com.alpermelkeli.socialmediaapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Comment() {
    val scope = rememberCoroutineScope() // CoroutineScope oluşturuyoruz
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                Text(text = "Hi there!")
                Text(text = "Hi there!")
                Text(text = "Hi there!")
                Text(text = "Hi there!")
                Text(text = "Hi there!")
                Text(text = "Hi there!")
                Text(text = "Hi there!")
            }
        },
        sheetPeekHeight = 0.dp, // Başlangıçta kapanmış şekilde
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                // CoroutineScope kullanarak bottom sheet'i açma/kapatma işlemini başlatıyoruz
                scope.launch {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
            }) {
                Text(text = "Show Bottom Sheet")
            }
        }
    }
}
