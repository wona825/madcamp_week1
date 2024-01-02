package com.madcamp.phonebook.presentation.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.R
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.component.TextBox
import com.madcamp.phonebook.presentation.contact.viewModel.ContactUpdateViewModel
import com.madcamp.phonebook.presentation.contact.viewModel.ContactViewModel
import com.madcamp.phonebook.ui.theme.Brown200
import com.madcamp.phonebook.ui.theme.Brown400
import com.madcamp.phonebook.ui.theme.Gray100
import com.madcamp.phonebook.ui.theme.Gray200
import com.madcamp.phonebook.ui.theme.Gray400

@Composable
fun ContactDetailScreen(
    navController: NavController,
    contactViewModel: ContactViewModel
) {
    val screen = Screen()
    var isEditmode by remember { mutableStateOf(false) }
    val contactNumber by remember { mutableStateOf(navController.currentBackStackEntry?.arguments?.getString("contactNumber") ?: "") }
    val contact by remember { mutableStateOf(
        contactViewModel.contactList.find { it.phoneNumber == contactNumber } ?: Contact()
    )
    }
    var contactName by remember { mutableStateOf(contact.name) }
    var contactFavoriteStatus by remember { mutableStateOf(contact.favoriteStatus) }

    val contactUpdateViewModel = ContactUpdateViewModel(contact, contactViewModel)

    Column(
        modifier = Modifier
            .background(color = Color(0xFFC4BDAC))
            .padding(horizontal = 20.dp, vertical = 50.dp)
            .padding(end = 30.dp)
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                modifier = Modifier
                    .size(50.dp)
                    .clickable { navController.popBackStack() },
                contentDescription = "back_button",
                tint = Brown400
            )

            Image(
                painterResource(id = R.drawable.dear_my_logo),
                contentDescription = "dear_my_logo",
                modifier = Modifier.size(100.dp)
            )

            Icon(
                imageVector = if (contactFavoriteStatus) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        contactFavoriteStatus = !contactFavoriteStatus
                        contact.favoriteStatus = contactFavoriteStatus
                        contactUpdateViewModel.event(2)
                    },
                tint = Brown400,
                contentDescription = "contact_favorite_status"
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "* 이름",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )

            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        isEditmode = true
                    },
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    modifier = Modifier.size(20.dp),
                    contentDescription = "edit_mode",
                    tint = if (isEditmode) Gray200 else Brown400
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            TextBox(
                type = "name",
                text = contactName,
                width = 320.dp,
                height = 65.dp,
                boxColor = Gray100,
                readOnly = !isEditmode,
                onValueChange = { newText ->
                    contactName = newText
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "* 전화번호 (변경 불가)",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Brown400
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextBox(
                type = "phone number",
                text = formatPhoneNumber(contactNumber),
                width = 320.dp,
                height = 65.dp,
                boxColor = Gray100,
                readOnly = true,
                textColor = if (isEditmode) Gray400 else Color.Black
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(130.dp, 55.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Brown400)
                    .clickable {
                        contactViewModel.initiatePhoneCall(contactNumber)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "call_button",
                    tint = Brown200
                )
            }

            Box(
                modifier = Modifier
                    .size(130.dp, 55.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Brown400)
                    .clickable {
                        contactViewModel.initiateSendMessage(contactNumber)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "message_button",
                    tint = Brown200
                )
            }
        }
        Spacer(modifier = Modifier.height(25.dp))

        if (isEditmode) {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Brown400)
                    .border(1.dp, Color.Transparent, RoundedCornerShape(4))
                    .clickable {
                        contact.name = contactName
                        contactUpdateViewModel.event(1)
                        isEditmode = false
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "저장하기",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Brown200
                )
            }
        }
    }
}