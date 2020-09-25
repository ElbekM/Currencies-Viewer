package com.example.currenciesviewer.database

import android.content.Context
import androidx.room.*

@Database(entities = [ValuteInfoEntity::class], exportSchema = false, version = 1)
@TypeConverters(ValuteConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): ValuteInfoDao

    companion object {
        fun newInstance(context: Context): AppDatabase =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "valutes.db")
                .build()
    }
}