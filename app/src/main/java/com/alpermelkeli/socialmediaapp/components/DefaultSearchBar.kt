package com.alpermelkeli.socialmediaapp.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultSearchBar(searchText:String, onQueryChange:(query:String)->Unit, ){

    SearchBar(
        query = searchText,
        onQueryChange = { onQueryChange(it) },
        onSearch = { /* Trigger search */ },
        active = false,
        onActiveChange = {},
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.background,),
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search", tint = MaterialTheme.colorScheme.secondary) },
        placeholder = { Text(text = "Search", color = MaterialTheme.colorScheme.secondary) },
        modifier = Modifier.height(50.dp),
        shape = ShapeDefaults.Small
    ){}

}