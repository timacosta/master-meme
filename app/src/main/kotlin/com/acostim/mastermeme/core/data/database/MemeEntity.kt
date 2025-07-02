package com.acostim.mastermeme.core.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.acostim.mastermeme.core.domain.Meme
import java.time.LocalDateTime

@Entity(tableName = "memes")
data class MemeEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val path: String,
    val name: String,
    val date: LocalDateTime = LocalDateTime.now(),
    val isFavorite: Boolean = false
)

fun MemeEntity.toDomain(): Meme {
    return Meme(
        uid = uid,
        path = path,
        name = name,
        date = date,
        isFavorite = isFavorite
    )
}

fun Meme.toEntity(): MemeEntity {
    return MemeEntity(
        uid = uid,
        path = path,
        name = name,
        date = date,
        isFavorite = isFavorite
    )
}
