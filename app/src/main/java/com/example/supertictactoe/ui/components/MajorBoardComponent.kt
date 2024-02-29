package com.example.supertictactoe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.supertictactoe.R
import com.example.supertictactoe.data.DefaultDataSource
import com.example.supertictactoe.ui.model.MajorBoard
import com.example.supertictactoe.ui.screens.GameStatus

@Preview(showBackground = true)
@Composable
fun MajorBoardComponent(
    modifier: Modifier = Modifier,
    board: MajorBoard = DefaultDataSource.emptyBoard,
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
                    val minorBoard = board.grid[outerIndex][innerIndex]
                    Box(
                        modifier = Modifier.padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (minorBoard.status == GameStatus.AvailableToPlay) {
                            MinorBoardComponent(
                                board = minorBoard,
                                modifier = Modifier,
                                onSquareClick = onSquareClick
                            )
                        } else {
                            Box(contentAlignment = Alignment.Center) {
                                MinorBoardComponent(
                                    board = minorBoard,
                                    modifier = Modifier,
                                    onSquareClick = onSquareClick
                                )
                                val minorBoardStatus = when (minorBoard.status) {
                                    GameStatus.WinnerX -> {
                                        R.drawable.x
                                    }

                                    GameStatus.WinnerO -> {
                                        R.drawable.o
                                    }

                                    else -> {
                                        R.drawable.dash
                                    }
                                }
                                Image(
                                    painter = painterResource(id = minorBoardStatus),
                                    contentDescription = null,
                                    modifier = Modifier.size(60.dp),
                                    alpha = 0.7f
                                )
                            }
                        }
                    }
                    if (innerIndex < 2) {
                        MiniBoardSeparatingLine()
                    }
                }
            }
            if (outerIndex < 2) {
                MiniBoardRowSeparatingLine()
            }
        }
    }
}

@Composable
fun MiniBoardSeparatingLine(modifier: Modifier = Modifier) {
    Spacer(
        modifier = Modifier
            .width(6.dp)
            .height(112.dp)
            .background(Color.Black)
    )
}

@Composable
fun MiniBoardRowSeparatingLine(modifier: Modifier = Modifier) {
    Spacer(
        modifier = Modifier
            .height(6.dp)
            .width(322.dp)
            .background(Color.Black)
    )
}