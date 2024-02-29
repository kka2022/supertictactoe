package com.example.supertictactoe.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.supertictactoe.data.DefaultDataSource
import com.example.supertictactoe.ui.model.MajorBoard
import com.example.supertictactoe.ui.model.MinorBoard
import com.example.supertictactoe.ui.model.Square

enum class Player(val symbol: String) {
    X("x"),
    O("o")
}

class GameViewModel : ViewModel() {
    private val _gameUiState: MutableState<GameUiState> = mutableStateOf(GameUiState())
    val gameUiState: MutableState<GameUiState> = _gameUiState

    fun resetGame() {
        _gameUiState.value = GameUiState()
    }

    fun updateSquare(id: String, squarePosition: Int) {
        val newGrid = MajorBoard(
            grid = _gameUiState.value.majorBoard.grid.map { minorBoardsList ->
                minorBoardsList.map { minorBoard ->
                    val newMinorGrid = updatedMinorGrid(squareId = id, minorBoard = minorBoard)

                    val newMinorBoardGameStatus = getGameStatus(newMinorGrid.map {
                        it.map {square ->
                            when (square.playerSymbol) {
                                "x" -> {
                                    GameStatus.WinnerX
                                }
                                "o" -> {
                                    GameStatus.WinnerO
                                }
                                else -> {
                                    GameStatus.AvailableToPlay
                                }
                            }
                        }
                    })
                    val minorBoardWithUpdatedStatus = minorBoard.copy(
                        grid = newMinorGrid,
                        status = newMinorBoardGameStatus)

                    minorBoardWithUpdatedStatus
                }
            }
        )

        var currentBoard: MinorBoard = DefaultDataSource.minorEmptyBoard
        newGrid.grid.forEach { minorBoardsList ->
            minorBoardsList.forEach { minorBoard ->
                if (minorBoard.position == squarePosition) {
                    currentBoard = minorBoard.copy()
                }
            }
        }

        val gridWithUpdatedMinorBoardsStatus = MajorBoard(
            grid = if (currentBoard.status == GameStatus.AvailableToPlay) {
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
                            isActive = minorBoard.status == GameStatus.AvailableToPlay
                        )
                    }
                }
            }
        )

        val newGameStatus = getGameStatus(gridWithUpdatedMinorBoardsStatus.grid.map {
            it.map {minorBoard ->
                minorBoard.status
            }
        })
        _gameUiState.value = _gameUiState.value.copy(
            majorBoard = gridWithUpdatedMinorBoardsStatus,
            currentPlayer = togglePlayer(_gameUiState.value.currentPlayer),
            numberOfMoves = _gameUiState.value.numberOfMoves + 1,
            gameStatus = newGameStatus
        )
    }

    private fun togglePlayer(player: Player): Player {
        return if (player == Player.X) Player.O else Player.X
    }

    private fun getGameStatus(gridToCheck: List<List<GameStatus>>): GameStatus {

        var newGameStatus = GameStatus.AvailableToPlay

        for (row in gridToCheck) {
            if (row.all { it == GameStatus.WinnerX }) {
                newGameStatus = GameStatus.WinnerX
            }
            if (row.all { it == GameStatus.WinnerO }) {
                newGameStatus = GameStatus.WinnerO
            }
        }

        for (col in gridToCheck.indices) {
            if (gridToCheck.all { it[col] == GameStatus.WinnerX }) {
                newGameStatus = GameStatus.WinnerX
            }
            if (gridToCheck.all { it[col] == GameStatus.WinnerO }) {
                newGameStatus = GameStatus.WinnerO
            }
        }

        // Check diagonals
        if ((gridToCheck.indices).all { gridToCheck[it][it] == GameStatus.WinnerX }) {
            newGameStatus = GameStatus.WinnerX
        }
        if ((gridToCheck.indices).all { gridToCheck[it][it] == GameStatus.WinnerO }) {
            newGameStatus = GameStatus.WinnerO
        }

        if ((gridToCheck.indices).all { gridToCheck[it][gridToCheck.size - 1 - it] == GameStatus.WinnerX }) {
            newGameStatus = GameStatus.WinnerX
        }
        if ((gridToCheck.indices).all { gridToCheck[it][gridToCheck.size - 1 - it] == GameStatus.WinnerO }) {
            newGameStatus = GameStatus.WinnerO
        }

        // Check Draw
        val isAnySquareEmpty =
            gridToCheck.any { subList -> subList.contains(GameStatus.AvailableToPlay) }
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
    val currentPlayer: Player = Player.X,
    val numberOfMoves: Int = 0,
    val gameStatus: GameStatus = GameStatus.AvailableToPlay
)