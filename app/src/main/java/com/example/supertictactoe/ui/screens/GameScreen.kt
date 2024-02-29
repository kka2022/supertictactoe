package com.example.supertictactoe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import com.example.supertictactoe.ui.components.MajorBoardComponent

@Preview(showBackground = true)
@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val gameViewModel = viewModel<GameViewModel>()
        val gameUiState = gameViewModel.gameUiState.value
        val board = gameUiState.majorBoard
        val currentPlayer = gameUiState.currentPlayer

        WhoseTurn(playerSymbol = currentPlayer.symbol)
        VerticalSpacingBetweenElements()
        Box(
            modifier = Modifier
                .size(360.dp), contentAlignment = Alignment.Center
        ) {
            MajorBoardComponent(
                board = board,
                onSquareClick = { id, squarePosition, _ ->
                    if (gameUiState.gameStatus == GameStatus.AvailableToPlay) {
                        gameViewModel.updateSquare(id, squarePosition)
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
        VerticalSpacingBetweenElements()
        ResetButton(onClick = { gameViewModel.resetGame() })
    }
}


@Composable
fun WhoseTurn(modifier: Modifier = Modifier, playerSymbol: String) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Text(text = "Turn: $playerSymbol", fontSize = 48.sp)
    }
}

@Composable
fun VerticalSpacingBetweenElements(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .height(16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ResetButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Reset Game", fontSize = 20.sp)
    }
}