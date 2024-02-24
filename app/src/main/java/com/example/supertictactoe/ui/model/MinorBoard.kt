package com.example.supertictactoe.ui.model

data class MinorBoard(
    val position: Int,
    val grid: List<List<Square>>,
    val isActive: Boolean
)