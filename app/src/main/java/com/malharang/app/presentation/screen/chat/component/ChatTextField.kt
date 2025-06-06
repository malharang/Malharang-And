package com.malharang.app.presentation.screen.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.malharang.app.R
import com.malharang.app.ui.theme.MalHaRangTheme.colors
import com.malharang.app.ui.theme.MalHaRangTheme.typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTextField(
    chat: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                colors.white
            )
            .padding(horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BasicTextField(
                value = chat,
                onValueChange = onTextChanged,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                textStyle = typography.bodyMedium,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                visualTransformation = VisualTransformation.None,
                singleLine = true,
                interactionSource = interactionSource,
                decorationBox = { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = chat,
                        visualTransformation = VisualTransformation.None,
                        innerTextField = innerTextField,
                        singleLine = true,
                        enabled = true,
                        interactionSource = interactionSource,
                        placeholder = {
                            Text(
                                text = "Type your reply...", // 또는 stringResource(R.string.chat_text_field_placeholder)
                                style = typography.bodyMedium,
                                color = colors.grayDark
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = colors.black,
                            unfocusedTextColor = colors.black,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        )
                    )
                }
            )

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_chat_mike_24),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(colors.greenTint)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chat_send_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(colors.white),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(6.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ChatTextFieldPreview() {
    MaterialTheme {
        ChatTextField(
            chat = "",
            onTextChanged = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}