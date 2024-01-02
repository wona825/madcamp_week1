package com.madcamp.phonebook.navigation

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.madcamp.phonebook.presentation.contact.ContactDetailScreen
import com.madcamp.phonebook.presentation.contact.ContactListScreen
import com.madcamp.phonebook.presentation.contact.viewModel.ContactViewModel
import com.madcamp.phonebook.presentation.TabLayout
import com.madcamp.phonebook.MainActivity.favorites
import com.madcamp.phonebook.presentation.gallery.GalleryScreen
import com.madcamp.phonebook.presentation.gallery.ImageDetailScreen


@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph(
    activity: ComponentActivity,
    contactViewModel: ContactViewModel,
    favoriteList: MutableList<favorites>
) {
    val screen = Screen()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = screen.MainScreen
    ) {
        composable(screen.MainScreen) {
            TabLayout(contactViewModel, favoriteList, navController)
        }

        composable(screen.ContactListScreen) {
            ContactListScreen(
                navController = navController,
                contactViewModel = contactViewModel
            )
        }

        composable(screen.ContactDetailScreen + "/{contactNumber}",
            arguments = listOf(navArgument("contactNumber") { type = NavType.StringType })
        ) {
            ContactDetailScreen(
                navController = navController,
                contactViewModel = contactViewModel
            )
        }
        composable(screen.GalleryScreen){
            GalleryScreen(navController, favoriteList)
        }

        composable(screen.ImageDetailScreen+"/{index}", arguments = listOf(navArgument("index") { type = NavType.IntType })) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: -1
            ImageDetailScreen(navController, favoriteList, favoriteList[index])
        }

    }
}

