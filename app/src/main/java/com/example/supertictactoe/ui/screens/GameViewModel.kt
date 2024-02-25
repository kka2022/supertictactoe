package com.example.supertictactoe.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.supertictactoe.data.DefaultDataSource
import com.example.supertictactoe.ui.model.MajorBoard
import com.example.supertictactoe.ui.model.MinorBoard
import com.example.supertictactoe.ui.model.Square

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

    fun updateSquare(id: String, squarePosition: Int, boardPosition: Int) {
        val newGrid = MajorBoard(
            grid = _gameUiState.value.majorBoard.grid.map { minorBoardsList ->
                minorBoardsList.map { minorBoard ->
                    val newMinorGrid = updatedMinorGrid(squareId = id, minorBoard = minorBoard)
                    val newMinorBoard = minorBoard.copy(
                        grid = newMinorGrid,
                        isActive = squarePosition == minorBoard.position
                    )
                    newMinorBoard
                }
            }
        )
        _gameUiState.value = _gameUiState.value.copy(
            majorBoard = newGrid,
            currentPlayer = togglePlayer(_gameUiState.value.currentPlayer),
            numberOfMoves = _gameUiState.value.numberOfMoves + 1
        )
    }

    private fun togglePlayer(player: Player): Player {
        return if (player == Player.PlayerX) Player.PlayerO else Player.PlayerX
    }


    private fun updatedMinorGrid(squareId: String, minorBoard: MinorBoard): List<List<Square>> {
        return minorBoard.grid.map { squaresList ->
            squaresList.map { square ->
                val newSquare = if (square.id == squareId && square.isEmpty) {
                    square.copy(
                        playerSymbol = _gameUiState.value.currentPlayer.symbol,
                        isEmpty = false
                    )
                } else {
                    square
                }
                newSquare
            }
        }
    }
}

data class GameUiState(
    val majorBoard: MajorBoard = DefaultDataSource.emptyBoard,
    val currentPlayer: Player = Player.PlayerX,
    val numberOfMoves: Int = 0
)