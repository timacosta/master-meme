package com.acostim.mastermeme.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MemeEntity::class], version = 1)
@TypeConverters(LocalDateTimeConverter::class)
abstract class MasterMemeDatabase: RoomDatabase() {
    abstract fun memeDao(): MemeDao
}