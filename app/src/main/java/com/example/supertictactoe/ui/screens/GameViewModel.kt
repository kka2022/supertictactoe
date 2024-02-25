package com.example.supertictactoe.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.supertictactoe.data.DefaultDataSource
import com.example.supertictactoe.ui.model.MajorBoard
import com.example.supertictactoe.ui.model.MinorBoard
import com.example.supertictactoe.ui.model.MinorBoardStatus
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
                    val minorBoardWithUpdatedStatus = updateMinorBoardStatus(newMinorBoard)
                    minorBoardWithUpdatedStatus
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

    private fun updateMinorBoardStatus(minorBoard: MinorBoard): MinorBoard {
        val gridToCheck = minorBoard.grid.map {
            it.map { square ->
                square.playerSymbol
            }
        }
        var newMinorBoardStatus = minorBoard.minorBoardStatus

        for (row in gridToCheck) {
            if (row.all { it == "x" }) {
                newMinorBoardStatus = MinorBoardStatus.WinnerX
            }
            if (row.all { it == "o" }) {
                newMinorBoardStatus = MinorBoardStatus.WinnerO
            }
        }

        for (col in gridToCheck.indices) {
            if (gridToCheck.all { it[col] == "x" }) {
                newMinorBoardStatus = MinorBoardStatus.WinnerX
            }
            if (gridToCheck.all { it[col] == "o" }) {
                newMinorBoardStatus = MinorBoardStatus.WinnerO
            }
        }

        // Check diagonals
        if ((gridToCheck.indices).all { gridToCheck[it][it] == "x" }) {
            newMinorBoardStatus = MinorBoardStatus.WinnerX
        }
        if ((gridToCheck.indices).all { gridToCheck[it][it] == "o" }) {
            newMinorBoardStatus = MinorBoardStatus.WinnerO
        }

        if ((gridToCheck.indices).all { gridToCheck[it][gridToCheck.size - 1 - it] == "x" }) {
            newMinorBoardStatus = MinorBoardStatus.WinnerX
        }
        if ((gridToCheck.indices).all { gridToCheck[it][gridToCheck.size - 1 - it] == "o" }) {
            newMinorBoardStatus = MinorBoardStatus.WinnerO
        }

        //        TODO: Handle Draw Condition
        val isAnySquareEmpty = gridToCheck.any { subList -> subList.contains("") }
        if (!isAnySquareEmpty && (newMinorBoardStatus != MinorBoardStatus.WinnerO && newMinorBoardStatus != MinorBoardStatus.WinnerX)) {
            newMinorBoardStatus = MinorBoardStatus.Draw
        }

        return minorBoard.copy(
            minorBoardStatus = newMinorBoardStatus
        )
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