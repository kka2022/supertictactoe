package com.example.supertictactoe.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.supertictactoe.data.DefaultDataSource
import com.example.supertictactoe.ui.model.MajorBoard
import com.example.supertictactoe.ui.model.MinorBoardStatus

@Preview(showBackground = true)
@Composable
fun TicTacToeMajorBoard(
    modifier: Modifier = Modifier,
    board: MajorBoard = DefaultDataSource.emptyBoard,
    onSquareClick: (String, Int, Int) -> Unit = { id, squarePosition, boardPosition -> }
) {
    Column(modifier = modifier) {
        for (row in board.grid) {
            Row {
                for (minorBoard in row) {
                    if (minorBoard.minorBoardStatus == MinorBoardStatus.AvailableToPlay) {
                        TicTacToeMinorBoard(
                            board = minorBoard,
                            modifier = Modifier,
                            onSquareClick = onSquareClick
                        )
                    } else {
                        Box(contentAlignment = Alignment.Center) {
                            TicTacToeMinorBoard(
                                board = minorBoard,
                                modifier = Modifier,
                                onSquareClick = onSquareClick
                            )
                            val text = when (minorBoard.minorBoardStatus) {
                                MinorBoardStatus.WinnerX -> {
                                    "x"
                                }

                                MinorBoardStatus.WinnerO -> {
                                    "o"
                                }

                                else -> {
                                    "—"
                                }
                            }
                            Text(text = text, fontSize = 80.sp)
                        }
                    }
                }
            }
        }
    }
}