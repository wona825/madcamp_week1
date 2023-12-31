package com.madcamp.phonebook.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madcamp.phonebook.ui.theme.Brown400
import com.madcamp.phonebook.ui.theme.Gray100
import com.madcamp.phonebook.ui.theme.Gray400

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBox(
    text: String,
    placeholder: String,
    onClickIcon: () -> Unit = {},
    onValueChange: (String) -> Unit,
    submit: () -> Unit = {}
){
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
//            .size(370.dp, 50.dp)
//            .clip(RoundedCornerShape(50))
            .clickable {},
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        ),
        value = text,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        },
        onValueChange = onValueChange,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            placeholderColor = Gray400,
            backgroundColor = Gray100,
            cursorColor = Brown400
        ),
        trailingIcon = {
            Icon(Icons.Filled.Search, "", Modifier.clickable {
                onClickIcon()
                keyboardController?.hide()
            })
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                submit()
                keyboardController?.hide()
            }),
        readOnly = false
    )
}