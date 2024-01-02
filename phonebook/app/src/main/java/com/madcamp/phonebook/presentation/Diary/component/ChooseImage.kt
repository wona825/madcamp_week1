package com.madcamp.phonebook.presentation.Diary.component

import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.madcamp.phonebook.presentation.gallery.component.getScreenWidth
import com.madcamp.phonebook.presentation.gallery.favorites.favorites

@Composable
fun ChooseImage(newFavorites: favorites){

    var permFlag by remember{ mutableStateOf(false) } // Does permission flag is on or not?
    var permIsGrantedFlag by remember{ mutableStateOf(false) } // Does check is granted or not?
    var permPrecheckedFlag by remember{ mutableStateOf(false) }  // Onclick makes authority check and make pre_checked.
    var storageAccessFlag by remember{ mutableStateOf(false) }  // Onclick makes image accessing.
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var clickFlagImage by remember{ mutableStateOf(false) }
    val context = LocalContext.current
    val screenWidth = getScreenWidth(context)
    val imageSize = ((screenWidth.toDouble())/2).toInt()
    var imageUri by remember{mutableStateOf<Uri?>(null)}

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {
            uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            }
            else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
            clickFlagImage = true
            newFavorites.image = imageUri
        }

        storageAccessFlag = false
    }

    val write_external_storage_launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
                isGranted -> permIsGrantedFlag = isGranted
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .size(imageSize.dp)
            .clickable {
                permFlag = true
                storageAccessFlag = true
            }
            .padding(top = 20.dp)
    ) {
        if(clickFlagImage){
            bitmap.value?.let {btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(imageSize.dp)
                        .height(imageSize.dp)
                )
            }
        }
        else{
            FilledTonalButton(

                onClick = {
                    permFlag = true
                    storageAccessFlag = true
                },
                modifier = Modifier.align(Alignment.Center)

            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                )
            }
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