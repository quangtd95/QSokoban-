package com.quangtd.qsokoban.domain.repository

import android.content.Context
import com.quangtd.qsokoban.domain.model.Level

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
interface GameDataRepository {
    fun loadData(context: Context): ArrayList<Level>
    fun saveData(context: Context, level: Level)
    fun saveData(context: Context, levelList: ArrayList<Level>)
    fun loadData(context: Context, id: Int): Level
}