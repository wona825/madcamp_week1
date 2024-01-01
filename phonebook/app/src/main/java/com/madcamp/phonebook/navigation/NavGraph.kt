package com.madcamp.phonebook.navigation

import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.presentation.contact.ContactDetailScreen
import com.madcamp.phonebook.presentation.contact.ContactListScreen
import com.madcamp.phonebook.presentation.contact.viewmodel.ContactViewModel
import com.madcamp.phonebook.presentation.TabLayout
import com.madcamp.phonebook.presentation.gallery.component.Gallery_Tab
import com.madcamp.phonebook.MainActivity.favorites
import com.madcamp.phonebook.presentation.gallery.component.Image_Tab


@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph(
    activity: ComponentActivity,
    contactList: List<Contact>,
    contactViewModel: ContactViewModel,
    favoriteList: MutableList<favorites>,
    delete_index: Int
) {
    val screen = Screen()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = screen.MainScreen
    ) {
        composable(screen.MainScreen) {
            TabLayout(contactList, favoriteList, navController)
        }

        composable(screen.ContactListScreen) {
            ContactListScreen(
                navController = navController,
                contactList = contactList
            )
        }

        composable(screen.ContactDetailScreen + "/{contactNumber}",
            arguments = listOf(navArgument("contactNumber") { type = NavType.StringType })
        ) {
            ContactDetailScreen(
                navController = navController,
                contactList = contactList,
                contactViewModel = contactViewModel
            )
        }
        composable(screen.GalleryScreen){
            Gallery_Tab(navController, favoriteList)
        }

        composable(screen.GalleryDetailScreen+"/{index}", arguments = listOf(navArgument("index") { type = NavType.IntType })) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: -1
            Image_Tab(navController, favoriteList, delete_index, favoriteList[index])
        }

    }
}

