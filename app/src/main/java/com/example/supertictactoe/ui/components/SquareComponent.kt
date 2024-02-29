package com.example.supertictactoe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supertictactoe.R

@Preview(showBackground = true)
@Composable
fun SquareComponent(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    text: String = "o",
) {
    Box(modifier = modifier.size(30.dp), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(
                id = when (text) {
                    "x" -> {
                        R.drawable.x
                    }
                    "o" -> R.drawable.o
                    else -> R.drawable.white
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

