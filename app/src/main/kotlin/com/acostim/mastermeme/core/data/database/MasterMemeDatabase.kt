package com.acostim.mastermeme.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MemeEntity::class], version = 1)
abstract class MasterMemeDatabase: RoomDatabase() {
    abstract fun memeDao(): MemeDao
}