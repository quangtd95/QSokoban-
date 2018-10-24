package com.quangtd.qsokoban.domain.repository

import android.content.Context
import com.quangtd.qsokoban.domain.model.Level

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
interface GameDataRepository {
    fun loadGameData(context: Context): ArrayList<Level>
    fun saveGameData(context: Context, level: Level)
    fun saveGameData(context: Context, levelList: ArrayList<Level>)
    fun loadGameData(context: Context, id: Int): Level
    fun loadBoomNumber(context: Context): Int
    fun saveBoomNumber(context: Context, boom: Int)
    fun loadCoin(context: Context): Int
    fun saveCoin(context: Context, coin: Int)

}