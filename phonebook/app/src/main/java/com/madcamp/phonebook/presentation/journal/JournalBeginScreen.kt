package com.madcamp.phonebook.presentation.journal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.FilledTonalButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.gallery.favorites.favorites
import com.madcamp.phonebook.ui.theme.Blue400

@Composable
fun JournalBeginScreen(navController: NavController, favoriteList: MutableList<favorites>){

    val screen = Screen()

    Box(){
      Column(){

          // Empty Space
          Box(
              modifier = Modifier
                  .weight(3f)
                  .fillMaxSize(),
          ){

          }

          // Big Title
          Box(
              modifier = Modifier
                  .weight(3f)
                  .fillMaxSize(),
          ){
              Text(
                  text = "당신의 오늘은 어땠나요?",
                  fontSize = 35.sp,
                  color = Color.Black,
                  fontWeight = FontWeight.Bold,
                  textAlign = TextAlign.Center,
              )
          }

          // Small Title
          Box(
              modifier = Modifier
                  .weight(2f)
                  .fillMaxSize(),
          ){
              val quoteList = listOf("\"아프니까 청춘이다.\" - 김난도", "\"하루라도 책을 읽지 않으면 입안에 가시가 돋는다.\" - 안중근")

              Text(
                  text = quoteList.random() ,
                  fontSize = 15.sp,
                  color = Color.Gray,
                  textAlign = TextAlign.Center,
                  modifier = Modifier.align(Alignment.TopCenter)
              )
          }

          // Empty Space
          Box(
              modifier = Modifier
                  .weight(3f)
                  .fillMaxSize(),
          ){

          }

          // Button to write a journal
          Box(
              modifier = Modifier
                  .weight(1f)
                  .fillMaxSize(),
              contentAlignment = Alignment.Center
          ){
              FilledTonalButton(

                  onClick = {
                        navController.navigate(screen.JournalWritingScreen)
                  },
                  modifier = Modifier.align(Alignment.Center)

                  ) {
                  Text("소중한 오늘 기록하기")
              }
          }

          // Empty Space
          Box(
              modifier = Modifier
                  .weight(1f)
          ){

          }

      }
    }
}