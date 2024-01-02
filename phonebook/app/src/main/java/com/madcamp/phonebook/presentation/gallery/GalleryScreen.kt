package com.madcamp.phonebook.presentation.gallery

import android.Manifest
import android.net.Uri
import android.os.Build
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
import com.madcamp.phonebook.presentation.gallery.component.SearchImage
import com.madcamp.phonebook.presentation.gallery.component.ShowGalleryOnScreen
import com.madcamp.phonebook.MainActivity.favorites

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun GalleryScreen(navController: NavController, favoritelist: MutableList<favorites>){

    var permFlag by remember{ mutableStateOf(false) } // Does permission flag is on or not?
    var permIsGrantedFlag by remember{ mutableStateOf(false) } // Does check is granted or not?
    var permPrecheckedFlag by remember{ mutableStateOf(false) }  // Onclick makes authority check and make pre_checked.
    var storageAccessFlag by remember{ mutableStateOf(false) }  // Onclick makes image accessing.
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            favoritelist.add(favorites("#Favorite", imageUri, false, "description...", true))
        }
        storageAccessFlag = false
    }

    val write_external_storage_launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
                isGranted -> permIsGrantedFlag = isGranted
        }
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ){
        SearchImage(favoritelist = favoritelist)


        Box(
            modifier = Modifier.weight(8f)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(0.dp, 0.dp)
            ) {

                item {

                    ShowGalleryOnScreen(navController, favoritelist = favoritelist)

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
                    permFlag = true
                    storageAccessFlag = true

                },

            ) {
                Text("Add New Image")
            }

            if (permFlag && (!permPrecheckedFlag)) {
                write_external_storage_launcher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                permFlag = false
                permPrecheckedFlag = true
            }

            if (permIsGrantedFlag) {
                if (storageAccessFlag) {
                    SideEffect {
                        launcher.launch("image/*")
                    }
                }
            }

        }
    }
}