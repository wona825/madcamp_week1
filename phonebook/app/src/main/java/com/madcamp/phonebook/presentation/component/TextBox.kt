package com.madcamp.phonebook.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madcamp.phonebook.ui.theme.Brown400
import com.madcamp.phonebook.ui.theme.Gray400

@Composable
fun TextBox(
    type: String = "",
    text: String,
    width: Dp,
    height: Dp,
    onClick: () -> Unit = {},
    onValueChange: (String) -> Unit = {},
    boxColor: Color,
    textColor: Color = Color.Black,
    readOnly: Boolean,
    fontSize: Int = 22
) {
    TextField(
        modifier = Modifier
            .width(width)
            .height(height)
            .padding(0.dp)
            .clickable { onClick() }
            .border(1.dp, Brown400, RoundedCornerShape(20)),
        textStyle = TextStyle(fontSize = fontSize.sp, fontWeight = FontWeight.Bold),
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                text = type,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = textColor,
            cursorColor = Color.Black,
            placeholderColor = Gray400,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = boxColor
        ),
        shape = RoundedCornerShape(20),
        readOnly = readOnly
    )
}