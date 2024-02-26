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
                    )
                    val minorBoardWithUpdatedStatus =
                        updateMinorBoardStatus(newMinorBoard, squarePosition)
                    minorBoardWithUpdatedStatus
                }
            }
        )

        var currentBoard: MinorBoard = DefaultDataSource.minorEmptyBoard
        newGrid.grid.forEach() { minorBoardsList ->
            minorBoardsList.forEach { minorBoard ->
                if (minorBoard.position == squarePosition) {
                    currentBoard = minorBoard.copy()
                }
            }
        }

        val gridWithUpdatedMinorBoardsStatus = MajorBoard(
            grid = if (currentBoard.status == MinorBoardStatus.AvailableToPlay) {
                newGrid.grid.map { minorBoardList ->
                    minorBoardList.map { minorBoard ->
                        minorBoard.copy(
                            isActive = minorBoard.position == currentBoard.position
                        )
                    }
                }
            } else {
                newGrid.grid.map { minorBoardList ->
                    minorBoardList.map { minorBoard ->
                        minorBoard.copy(
                            isActive = minorBoard.status == MinorBoardStatus.AvailableToPlay
                        )
                    }
                }
            }
        )

        _gameUiState.value = _gameUiState.value.copy(
            majorBoard = gridWithUpdatedMinorBoardsStatus,
            currentPlayer = togglePlayer(_gameUiState.value.currentPlayer),
            numberOfMoves = _gameUiState.value.numberOfMoves + 1,
            gameStatus = getGameStatus(gridWithUpdatedMinorBoardsStatus)
        )
    }

    private fun togglePlayer(player: Player): Player {
        return if (player == Player.PlayerX) Player.PlayerO else Player.PlayerX
    }

    private fun updateMinorBoardStatus(minorBoard: MinorBoard, squarePosition: Int): MinorBoard {
        val gridToCheck = minorBoard.grid.map {
            it.map { square ->
                square.playerSymbol
            }
        }
        var newMinorBoardStatus = minorBoard.status

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

        // Check Draw
        val isAnySquareEmpty = gridToCheck.any { subList -> subList.contains("") }
        if (!isAnySquareEmpty && (newMinorBoardStatus != MinorBoardStatus.WinnerO && newMinorBoardStatus != MinorBoardStatus.WinnerX)) {
            newMinorBoardStatus = MinorBoardStatus.Draw
        }

        return minorBoard.copy(
            status = newMinorBoardStatus,
        )
    }

    private fun getGameStatus(majorBoard: MajorBoard): GameStatus {
        val gridToCheck = majorBoard.grid.map { minorBoardsList ->
            minorBoardsList.map { minorBoard ->
                minorBoard.status
            }
        }

        var newGameStatus = GameStatus.AvailableToPlay

        for (row in gridToCheck) {
            if (row.all { it == MinorBoardStatus.WinnerX }) {
                newGameStatus = GameStatus.WinnerX
            }
            if (row.all { it == MinorBoardStatus.WinnerO }) {
                newGameStatus = GameStatus.WinnerO
            }
        }

        for (col in gridToCheck.indices) {
            if (gridToCheck.all { it[col] == MinorBoardStatus.WinnerX }) {
                newGameStatus = GameStatus.WinnerX
            }
            if (gridToCheck.all { it[col] == MinorBoardStatus.WinnerO }) {
                newGameStatus = GameStatus.WinnerO
            }
        }

        // Check diagonals
        if ((gridToCheck.indices).all { gridToCheck[it][it] == MinorBoardStatus.WinnerX }) {
            newGameStatus = GameStatus.WinnerX
        }
        if ((gridToCheck.indices).all { gridToCheck[it][it] == MinorBoardStatus.WinnerO }) {
            newGameStatus = GameStatus.WinnerO
        }

        if ((gridToCheck.indices).all { gridToCheck[it][gridToCheck.size - 1 - it] == MinorBoardStatus.WinnerX }) {
            newGameStatus = GameStatus.WinnerX
        }
        if ((gridToCheck.indices).all { gridToCheck[it][gridToCheck.size - 1 - it] == MinorBoardStatus.WinnerO }) {
            newGameStatus = GameStatus.WinnerO
        }

        // Check Draw
        val isAnySquareEmpty =
            gridToCheck.any { subList -> subList.contains(MinorBoardStatus.AvailableToPlay) }
        if (!isAnySquareEmpty && (newGameStatus != GameStatus.WinnerO && newGameStatus != GameStatus.WinnerX)) {
            newGameStatus = GameStatus.Draw
        }
        return newGameStatus
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

enum class GameStatus {
    WinnerX,
    WinnerO,
    Draw,
    AvailableToPlay,
}

data class GameUiState(
    val majorBoard: MajorBoard = DefaultDataSource.emptyBoard,
    val currentPlayer: Player = Player.PlayerX,
    val numberOfMoves: Int = 0,
    val gameStatus: GameStatus = GameStatus.AvailableToPlay
)