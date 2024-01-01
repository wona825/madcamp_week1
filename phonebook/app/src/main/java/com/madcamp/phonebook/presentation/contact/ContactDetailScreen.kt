package com.madcamp.phonebook.presentation.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.navigation.Screen
import com.madcamp.phonebook.presentation.contact.component.TextBox
import com.madcamp.phonebook.presentation.contact.viewmodel.ContactViewModel
import com.madcamp.phonebook.ui.theme.Gray100
import com.madcamp.phonebook.ui.theme.Gray200
import com.madcamp.phonebook.ui.theme.Gray400

@Composable
fun ContactDetailScreen(
    navController: NavController,
    contactList: List<Contact>,
    contactViewModel: ContactViewModel
) {
    val screen = Screen()
    var isEditmode by remember { mutableStateOf(false) }
    var contactNumber by remember { mutableStateOf(navController.currentBackStackEntry?.arguments?.getString("contactNumber") ?: "") }
    var contact by remember { mutableStateOf(
        contactList.find { it.phoneNumber == contactNumber } ?: Contact()
        )
    }
    var contactName by remember { mutableStateOf(contact.name) }
    var contactFavoriteStatus by remember { mutableStateOf(contact.favoriteStatus) }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 50.dp)
            .padding(end = 30.dp)
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
                tint = Color.Black
            )

            Icon(
                imageVector = if (contactFavoriteStatus) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        contactFavoriteStatus = !contactFavoriteStatus
                    },
                contentDescription = "contact_star",
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        Box(
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    isEditmode = true
                }
                .align(Alignment.End)
        ) {
            Icon(
                imageVector = Icons.Filled.Create,
                modifier = Modifier.size(20.dp),
                contentDescription = "edit_mode",
                tint = if (isEditmode) Gray400 else Color.Black
            )
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
                text = "* 관련 정보 이슈로 전화번호는 변경이 불가합니다.",
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = Gray400
            )

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
        Spacer(modifier = Modifier.height(70.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp, 55.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Gray200)
                    .border(1.dp, Color.Black, RoundedCornerShape(50))
                    .clickable {
                        contactViewModel.initiatePhoneCall(contactNumber)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "call_button",
                    tint = Color.Black
                )
            }

            Box(
                modifier = Modifier
                    .size(120.dp, 55.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Gray200)
                    .border(1.dp, Color.Black, RoundedCornerShape(50))
                    .clickable {
                        contactViewModel.initiateSendMessage(contactNumber)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    modifier = Modifier.size(30.dp),
                    contentDescription = "message_button",
                    tint = Color.Black
                )
            }
        }

    }
}
