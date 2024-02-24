package com.example.supertictactoe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.supertictactoe.data.DefaultDataSource
import com.example.supertictactoe.ui.model.MajorBoard

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
                    TicTacToeMinorBoard(
                        board = minorBoard,
                        modifier = Modifier,
                        onSquareClick = onSquareClick
                    )
                }
            }
        }
    }
}