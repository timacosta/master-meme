package com.acostim.mastermeme.core.domain

import java.time.LocalDateTime

data class Meme(
    val uid: Int = 0,
    val path: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val isFavorite: Boolean = false,
    val isSelected: Boolean = false
)
