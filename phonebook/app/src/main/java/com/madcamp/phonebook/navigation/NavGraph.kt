package com.madcamp.phonebook.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NavGraph(
    activity: ComponentActivity,
    contactList: List<Contact>,
    contactViewModel: ContactViewModel
) {
    val screen = Screen()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = screen.MainScreen
    ) {
        composable(screen.MainScreen) {
            TabLayout(contactList, navController)
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
    }
}

