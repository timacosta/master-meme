package com.acostim.mastermeme.core.data.database

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface MemeDao {
    @Insert
    suspend fun insert(meme: MemeEntity)

    @Query("SELECT * FROM memes")
    fun getSavedMemes(): Flow<MemeEntity>
}