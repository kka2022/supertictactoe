package com.example.supertictactoe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun MinorBoardComponent(
    modifier: Modifier = Modifier,
    board: MinorBoard = DefaultDataSource.minorEmptyBoard,
    onSquareClick: (String, Int, Int) -> Unit = { _, _, _ -> }
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (outerIndex in board.grid.indices) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                for (innerIndex in board.grid[outerIndex].indices) {
                    val square = board.grid[outerIndex][innerIndex]
                    SquareComponent(
                        isActive = board.isActive,
                        text = square.playerSymbol,
                        modifier = Modifier.then(
                            if (board.isActive) {
                                Modifier.clickable {
                                    if (square.isEmpty) {
                                        onSquareClick(
                                            square.id,
                                            board.grid[outerIndex][innerIndex].position,
                                            board.position
                                        )
                                    }
                                }
                            } else {
                                Modifier
                            }
                        )
                    )
                    if (innerIndex < 2) {
                        SquareSeparatingLine(color = if (board.isActive) Color(8, 207, 17, 255) else Color.Gray)
                    }
                }
            }
            if (outerIndex < 2) {
                RowSeparatingLine(color = if (board.isActive) Color(8, 207, 17, 255) else Color.Gray)
            }
        }
    }
}

@Composable
fun SquareSeparatingLine(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Spacer(
        modifier = Modifier
            .width(2.dp)
            .height(30.dp)
            .background(color)
    )
}

@Composable
fun RowSeparatingLine(modifier: Modifier = Modifier, color: Color = Color.Black) {
    Spacer(
        modifier = Modifier
            .height(2.dp)
            .width(90.dp)
            .background(color)
    )
}