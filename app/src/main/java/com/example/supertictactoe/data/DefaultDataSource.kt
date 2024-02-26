package com.example.supertictactoe.data

import com.example.supertictactoe.ui.model.MajorBoard
import com.example.supertictactoe.ui.model.MinorBoard
import com.example.supertictactoe.ui.model.MinorBoardStatus
import com.example.supertictactoe.ui.model.Square

object DefaultDataSource {
    private const val boardSize = 3
    private var minorBoardInitialPosition = 1

    val emptyBoard: MajorBoard = MajorBoard(
        grid = List(boardSize) {
            List(boardSize) {
                var position = 1
                MinorBoard(
                    position = minorBoardInitialPosition++,
                    grid = List(boardSize) {
                        List(boardSize) {
                            Square(
                                playerSymbol = "",
                                isEmpty = true,
                                position = position++
                            )
                        }
                    },
                    isActive = true,
                    status = MinorBoardStatus.AvailableToPlay
                )
            }
        }
    )

    val minorEmptyBoard: MinorBoard = MinorBoard(
        position = 1,
        grid = List(boardSize) {
            var position = 1
            List(boardSize) {
                Square(playerSymbol = "p", isEmpty = true, position = position++)
            }
        },
        isActive = true,
        status = MinorBoardStatus.AvailableToPlay
    )
}