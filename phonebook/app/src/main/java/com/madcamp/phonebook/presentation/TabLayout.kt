package com.madcamp.phonebook.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.ui.theme.Blue400
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabLayout(contactList: List<Contact>) {
    val pagerState = rememberPagerState(pageCount = 3)

    Column(
        modifier = Modifier.background(Color.White)
    ) {
        TopAppBar(backgroundColor = Blue400) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "❀´▽`❀ ",
                    style = TextStyle(color = Color.White),
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h6.fontSize,
                    modifier = Modifier.padding(all = 5.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, contactList = contactList)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Friends" to Icons.Default.Call,
        "Favorites" to Icons.Default.Favorite,
        "Settings" to Icons.Default.Settings
    )
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Blue400,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.White
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                icon = {
                    Icon(imageVector = list[index].second, contentDescription = null)
                },
                text = {
                    Text(
                        list[index].first,
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, contactList: List<Contact>) {

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> ContactListScreen(contactList)
            1 -> {}
            2 -> TabContentScreen(data = "Welcome to Screen 3")
        }
    }
}


@Composable
fun TabContentScreen(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = data,
            style = MaterialTheme.typography.h5,
            color = Blue400,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}