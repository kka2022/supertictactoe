package com.example.supertictactoe.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeButton(
    isActive: Boolean,
    modifier: Modifier = Modifier,
    text: String = "o"
) {
    Surface(
        modifier = modifier.size(40.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isActive) Color.Green else Color.LightGray
        )
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = text)
        }
    }
}
