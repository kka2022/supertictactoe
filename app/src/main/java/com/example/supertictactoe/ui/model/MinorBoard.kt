package com.example.supertictactoe.ui.model

import com.example.supertictactoe.ui.screens.GameStatus

data class MinorBoard(
    val position: Int,
    val grid: List<List<Square>>,
    val isActive: Boolean,
    val status: GameStatus
)
