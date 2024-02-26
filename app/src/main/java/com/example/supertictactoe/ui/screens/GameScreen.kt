package com.example.supertictactoe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Box(modifier = Modifier.size(360.dp)) {
                TicTacToeMajorBoard(
                    board = board,
                    onSquareClick = { id, squarePosition, boardPosition ->
                        if (gameUiState.gameStatus == GameStatus.AvailableToPlay) {
                            gameViewModel.updateSquare(id, squarePosition, boardPosition)
                        }
                    }
                )
                if (gameUiState.gameStatus != GameStatus.AvailableToPlay) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(234, 234, 234, 184)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(modifier = Modifier.clip(RoundedCornerShape(16.dp))) {
                            Text(
                                text = gameUiState.gameStatus.name,
                                fontSize = 50.sp,
                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { gameViewModel.resetGame() }) {
                Text(text = "Reset Game")
            }
        }
    }
}