package com.example.supertictactoe.ui.model

import java.util.UUID

data class Square(
    val id: String = UUID.randomUUID().toString(),
    val playerSymbol: String,
    val isEmpty: Boolean,
    val position: Int
)