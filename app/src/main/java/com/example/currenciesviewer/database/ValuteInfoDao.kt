package com.example.currenciesviewer.database

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ValuteInfoDao {
    @Query("SELECT * FROM valuteInfoEntity")
    fun getAll(): Single<ValuteInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateValuteInfo(valuteInfo: ValuteInfoEntity): Completable
}
