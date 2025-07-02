package com.acostim.mastermeme.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemeDao {
    @Insert
    suspend fun insert(meme: MemeEntity)

    @Query("SELECT * FROM memes")
    fun getSavedMemes(): Flow<List<MemeEntity>>

    @Query("UPDATE memes SET isFavorite = :isFavorite WHERE uid = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM memes WHERE uid IN (:ids)")
    suspend fun deleteMeme(ids: List<Int>)
}