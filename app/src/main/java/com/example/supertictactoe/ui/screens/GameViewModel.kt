package com.example.supertictactoe.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.supertictactoe.data.DefaultDataSource
import com.example.supertictactoe.ui.model.MajorBoard

enum class Player(val symbol: String) {
    PlayerX("x"),
    PlayerO("o")
}

class GameViewModel : ViewModel() {
    private val _gameUiState: MutableState<GameUiState> = mutableStateOf(GameUiState())
    val gameUiState: MutableState<GameUiState> = _gameUiState

    fun resetGame() {
        _gameUiState.value = GameUiState()
    }

    fun updateSquare(id: String, squarePosition: Int) {
        val newGrid = MajorBoard(
            grid = _gameUiState.value.wholeGrid.grid.map { minorBoardsList ->
                minorBoardsList.map { minorBoard ->
                    val newMinorGrid = minorBoard.grid.map { squaresList ->
                        squaresList.map { square ->
                            if (square.id == id) {
                                if (square.isEmpty) {
                                    square.copy(
                                        playerSymbol = _gameUiState.value.currentPlayer.symbol,
                                        isEmpty = false
                                    )
                                } else {
                                    square
                                }
                            } else {
                                square
                            }
                        }
                    }
                    minorBoard.copy(
                        grid = newMinorGrid,
                        isActive = squarePosition == minorBoard.position
                    )
                }
            }
        )
        _gameUiState.value = _gameUiState.value.copy(
            wholeGrid = newGrid,
            currentPlayer = if (_gameUiState.value.currentPlayer == Player.PlayerO) Player.PlayerX else Player.PlayerO
        )
    }
}

data class GameUiState(
    val wholeGrid: MajorBoard = DefaultDataSource.emptyBoard,
    val currentPlayer: Player = Player.PlayerX
)