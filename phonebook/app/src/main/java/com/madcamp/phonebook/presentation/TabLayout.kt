package com.madcamp.phonebook.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Diary
import com.madcamp.phonebook.presentation.contact.ContactListScreen
import com.madcamp.phonebook.presentation.contact.viewModel.ContactViewModel
import com.madcamp.phonebook.presentation.gallery.GalleryScreen
import com.madcamp.phonebook.presentation.Diary.DiaryBeginScreen
import com.madcamp.phonebook.presentation.Diary.viewmodel.DiaryViewModel
import com.madcamp.phonebook.ui.theme.Brown200
import com.madcamp.phonebook.ui.theme.Brown300
import com.madcamp.phonebook.ui.theme.Brown400
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.R)
@ExperimentalPagerApi
@Composable
fun TabLayout(
    ContactViewModel: ContactViewModel,
    diaryViewModel: DiaryViewModel,
    navController: NavController
) {
    val pagerState = rememberPagerState(pageCount = 3)

    Column(
        modifier = Modifier.background(Color.White)
    ) {
        TopAppBar(backgroundColor = Brown300) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painterResource(id = R.drawable.dear_my_logo),
                    contentDescription = "notice",
                    modifier = Modifier.size(70.dp)
                )
            }
        }
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, ContactViewModel = ContactViewModel, diaryViewModel, navController = navController)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Friends" to Icons.Default.Call,
        "Favorites" to Icons.Default.Favorite,
        "Journal" to Icons.Default.Edit
    )
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Brown300,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Brown400
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                icon = {
                    Icon(imageVector = list[index].second, contentDescription = null,
                        tint = if (pagerState.currentPage == index) Brown400 else Brown200)
                },
                text = {
                    Text(
                        list[index].first,
                        color = if (pagerState.currentPage == index) Brown400 else Brown200
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

@RequiresApi(Build.VERSION_CODES.R)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    ContactViewModel: ContactViewModel,
    diaryViewModel: DiaryViewModel,
    navController: NavController,
    onClick: () -> Unit = {}
) {

    HorizontalPager(state = pagerState) { page ->

            when (page) {
                0 -> ContactListScreen(navController, ContactViewModel)
                1 -> GalleryScreen(navController = navController, diaryViewModel)
                2 -> DiaryBeginScreen(navController = navController, diaryViewModel)
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
            color = Brown300,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}