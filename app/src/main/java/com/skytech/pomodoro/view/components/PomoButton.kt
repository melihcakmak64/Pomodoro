package com.skytech.pomodoro.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PomoButton(
    height: Dp,
    width: Dp,
    backgroundColor: Color,
    buttonIcon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(height = height, width = width)
            .background(color = backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(15.dp)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = { onClick() })


    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = buttonIcon,
            contentDescription = "",
            tint = iconColor
        )
    }


}

