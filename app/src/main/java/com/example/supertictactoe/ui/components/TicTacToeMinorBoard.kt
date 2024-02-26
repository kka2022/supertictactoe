package com.example.supertictactoe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supertictactoe.data.DefaultDataSource
import com.example.supertictactoe.ui.model.MinorBoard

@Preview(showBackground = true)
@Composable
fun TicTacToeMinorBoard(
    modifier: Modifier = Modifier,
    board: MinorBoard = DefaultDataSource.minorEmptyBoard,
    onSquareClick: (String, Int, Int) -> Unit = { id, squarePosition, boardPosition -> }
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.border(
                width = 2.dp,
                color = Color.Black
            )
        ) {
            for (row in board.grid) {
                Row {
                    for (square in row) {
                        TicTacToeButton(
                            isActive = board.isActive,
                            text = square.playerSymbol,
                            modifier = Modifier.then(
                                if (board.isActive) {
                                    Modifier.clickable {
                                        if (square.isEmpty) {
                                            onSquareClick(
                                                square.id,
                                                square.position,
                                                board.position
                                            )
                                        }
                                    }
                                } else {
                                    Modifier
                                }
                            )
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, Color.Black)
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .border(10.dp, Color.White))
            }
        }
    }
}