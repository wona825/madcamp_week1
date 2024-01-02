package com.madcamp.phonebook.presentation.contact.contactComponent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.presentation.contact.formatPhoneNumber
import com.madcamp.phonebook.ui.theme.Gray100

@Composable
fun ContactDropDownBox(
    itemList : List<Contact>
){
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(itemList[0]) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    Column(){
        TextField(
            modifier = Modifier
                .width(370.dp)
                .height(50.dp)
                .padding(0.dp)
                .clip(RoundedCornerShape(50))
                .onGloballyPositioned { coordinates ->
                    textFieldSize = coordinates.size.toSize()
                },
            value = selected.name + " (" + formatPhoneNumber(selected.phoneNumber) + ")",
            onValueChange =  {
                selected = itemList.find { contact ->
                    "${contact.name} ${contact.phoneNumber}" == it
                }!!
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Gray100
            ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            ),
            trailingIcon = {
                Icon(icon, "", Modifier.clickable { expanded = !expanded })
            },
            readOnly = true
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            itemList.forEach { label ->
                DropdownMenuItem(onClick = {
                    selected = label
                    expanded = false
                }) {
                    Text(
                        text = label.name + " (" + formatPhoneNumber(label.phoneNumber) + ")",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}