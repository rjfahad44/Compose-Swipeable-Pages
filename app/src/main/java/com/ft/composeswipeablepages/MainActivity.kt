package com.ft.composeswipeablepages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ft.composeswipeablepages.ui.theme.ComposeSwipeablePagesTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animals = listOf(
            R.drawable.cat,
            R.drawable.dog,
            R.drawable.chicken,
        )
        setContent {
            ComposeSwipeablePagesTheme {
                val pagerState = rememberPagerState()
                val scope = rememberCoroutineScope()
                Box(modifier = Modifier.fillMaxSize()) {
                    HorizontalPager(
                        pageCount = animals.size,
                        state = pagerState,
                        key = { animals[it] },
                        pageSize = PageSize.Fill
                    ) { index ->
                        Image(
                            painter = painterResource(id = animals[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Box(
                        modifier = Modifier
                            .offset(y = -(16).dp)
                            .fillMaxWidth(0.5f)
                            .clip(RoundedCornerShape(100))
                            .background(Color.DarkGray)
                            .padding(8.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        IconButton(
                            onClick = {
                                val _pageState = if(pagerState.currentPage == 0) animals.size else pagerState.currentPage - 1
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        _pageState
                                    )
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.CenterStart)

                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                tint = Color.White,
                                contentDescription = "Go back"
                            )
                        }
                        IconButton(
                            onClick = {
                                val _pageState = if(pagerState.currentPage == animals.size-1) 0 else pagerState.currentPage + 1
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        _pageState
                                    )
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                tint = Color.White,
                                contentDescription = "Go forward"
                            )
                        }
                    }
                }
            }
        }
    }
}