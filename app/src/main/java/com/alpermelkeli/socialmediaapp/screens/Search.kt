package com.alpermelkeli.socialmediaapp.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.UserItem
import com.alpermelkeli.socialmediaapp.ui.theme.Grey20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(onUserItemClicked:(String)->Unit, onClickedOwnUserItem:()->Unit) {
    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val userViewModel = context.userViewModel
    val user by userViewModel.user.observeAsState()
    val searchResults by userViewModel.searchResults.observeAsState(emptyList())

    val columnScrollState = rememberLazyListState()

    var searchText by remember{ mutableStateOf("") }

    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(25.dp))

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.Center){

            SearchBar(
                query = searchText,
                onQueryChange = { query -> userViewModel.searchUsers(query)
                                searchText = query},
                onSearch = { /* Trigger search */ },
                active = false,
                onActiveChange = {},
                colors = SearchBarDefaults.colors(containerColor = Grey20),
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search") },
                placeholder = { Text(text = "Search") },
                modifier = Modifier.height(50.dp),
                shape = ShapeDefaults.Small
            ){}

        }
        Spacer(modifier = Modifier.height(30.dp))

        LazyColumn(Modifier.fillMaxSize(),
            state = columnScrollState) {
            items(searchResults){
                UserItem(profilePhoto = it.profilePhoto, userName = it.username){

                    if(user?.id == it.id){

                    }
                    else{
                        onUserItemClicked(it.id)
                    }
                }
            }
        }


    }
}