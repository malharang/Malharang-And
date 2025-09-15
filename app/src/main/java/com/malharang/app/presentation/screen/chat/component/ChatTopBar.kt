package com.malharang.app.presentation.screen.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.malharang.app.R
import com.malharang.app.core.util.noRippleClickable
import com.malharang.app.core.designsystem.theme.MalHaRangTheme.typography

@Composable
fun ChatTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_chat_arrow_back_black_24),
            contentDescription = null,
            modifier = Modifier.noRippleClickable { onBackClick() }
        )

        Text(
            text = title,
            style = typography.bodyMediumBold,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_chat_leaf_black_24),
            contentDescription = null
        )
    }
}
