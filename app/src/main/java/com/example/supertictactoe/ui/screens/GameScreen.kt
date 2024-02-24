package com.example.supertictactoe.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.supertictactoe.ui.components.TicTacToeMajorBoard

@Preview(showBackground = true)
@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val gameViewModel = viewModel<GameViewModel>()
            val gameUiState = gameViewModel.gameUiState.value
            val board = gameUiState.majorBoard
            val currentPlayer = gameUiState.currentPlayer

            Text(text = "${currentPlayer.symbol}'s turn")
            Spacer(modifier = Modifier.height(16.dp))
            TicTacToeMajorBoard(
                board = board,
                onSquareClick = { id, squarePosition, boardPosition ->
                    gameViewModel.updateSquare(id, squarePosition, boardPosition)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { gameViewModel.resetGame() }) {
                Text(text = "Reset Game")
            }
        }
    }
}