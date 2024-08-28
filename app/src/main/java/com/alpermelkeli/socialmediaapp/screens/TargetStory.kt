package com.alpermelkeli.socialmediaapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.alpermelkeli.socialmediaapp.SocialMediaApplication
import com.alpermelkeli.socialmediaapp.components.ShimmerEffectImage
import com.alpermelkeli.socialmediaapp.components.StoryTopBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun TargetStory(userId: String, onBackClicked:()->Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current.applicationContext as SocialMediaApplication

    val storiesViewModel = context.storiesViewModel

    val stories by storiesViewModel.homePageStories.observeAsState(emptyList())

    var currentPage by remember { mutableIntStateOf(0) }

    val targetStoriesPair = stories.find { it.first == userId }

    val pagerState = rememberPagerState(initialPage = stories.indexOf(targetStoriesPair)){stories.size}

    var targetStories by remember { mutableStateOf(stories[pagerState.currentPage].second) }

    LaunchedEffect(pagerState.currentPage) {
        currentPage = 0
        targetStories = stories[pagerState.currentPage].second
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = true
    ) {


        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Black,
            topBar = {
                StoryTopBar(
                    currentStoryIndex = currentPage,
                    totalStories = targetStories.size,
                    username = targetStories.get(0).username,
                    onBackClicked = {onBackClicked()},
                    profilePhoto = targetStories.get(0).profilePhoto,
                    onTimeFinished = {
                        if (currentPage < targetStories.size - 1) {
                            currentPage++
                        } else {
                            if (pagerState.currentPage  < stories.size-1) {
                                scope.launch { pagerState.animateScrollToPage(pagerState.targetPage + 1) }
                            } else {
                                onBackClicked()
                            }
                        }
                    }
                )
            }
        ){

            ShimmerEffectImage(
                modifier = Modifier
                    .fillMaxHeight(0.85f)
                    .fillMaxWidth()
                    .clip(ShapeDefaults.Large)
                    .padding(top = 30.dp),
                data = targetStories[currentPage].image
            )

            // Handle tapping on the left or right side to go to the previous or next story
            Row(Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            if (currentPage > 0) {
                                currentPage--
                            }
                        }
                        .background(Color.Transparent)
                )
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.5f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            if (currentPage < targetStories.size - 1) {
                                currentPage++
                            }
                        }
                        .background(Color.Transparent)
                )
            }
        }
    }
}

