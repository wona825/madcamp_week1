package com.madcamp.phonebook.presentation.gallery.component

import android.Manifest
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.madcamp.phonebook.Gallery_Navigation.Gallery_Tab.Gallery_Screen.SearchTab
import com.madcamp.phonebook.Gallery_Navigation.Gallery_Tab.Gallery_Screen.Show_Gallery_Image
import com.madcamp.phonebook.MainActivity.favorites

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun Gallery_Tab(navController: NavController, favoritelist: MutableList<favorites>){

    var Perm_flag by remember{ mutableStateOf(false) } // Does permission flag is on or not?
    var Check_isGranted by remember{ mutableStateOf(false) } // Does check is granted or not?
    var pre_checked by remember{ mutableStateOf(false) }  // Onclick makes authority check and make pre_checked.
    var add_image by remember{ mutableStateOf(false) }  // Onclick makes image accessing.
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            favoritelist.add(favorites("#Favorite", imageUri))
        }
        Log.d("add", "add: $favoritelist")
        add_image = false
    }

    val write_external_storage_launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
                isGranted -> Check_isGranted = isGranted
        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ){
        SearchTab(favoritelist = favoritelist)


        Box(
            modifier = Modifier.weight(8f)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(0.dp, 0.dp)
            ) {

                item {

                    Show_Gallery_Image(navController, favoritelist = favoritelist)

                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .offset(y = (-5).dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            FilledTonalButton(

                onClick = {
                    Perm_flag = true
                    add_image = true

                },

            ) {
                Text("Add New Image")
            }

            if (Perm_flag && (!pre_checked)) {
                write_external_storage_launcher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                Perm_flag = false
                pre_checked = true
            }

            if (Check_isGranted) {
                if (add_image) {
                    SideEffect {
                        launcher.launch("image/*")
                    }
                }
            }

        }
    }
}