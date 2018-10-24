package com.quangtd.qsokoban.domain.repository

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quangtd.qsokoban.common.CommonConstants
import com.quangtd.qsokoban.domain.game.enums.GameKind
import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.util.SharedPreferencesUtils
import java.lang.reflect.Type

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class GameDataRepositoryImpl : GameDataRepository {

    private var sharedPreferencesUtils: SharedPreferencesUtils = SharedPreferencesUtils.getInstance()

    override fun loadGameData(context: Context): ArrayList<Level> {
        //khởi tạo list các level
        val levelList = ArrayList<Level>()
        val data = sharedPreferencesUtils.getString(context, CommonConstants.KEY_GAME_DATA)
        //nếu data = null hoặc empty -> load lần đầu. set data rồi lưu luôn.
        if (TextUtils.isEmpty(data)) {
            for (i in 1..1000) {
                val l = Level(id = i, gameKind = GameKind.CLASSIC.value, isComplete = false, isUnlock = (i == 1), savedMove = 0, ranking = 0)
                levelList.add(l)
            }
            saveGameData(context, levelList)
        } else {
            val listType: Type = object : TypeToken<ArrayList<Level>>() {}.type
            levelList.addAll(Gson().fromJson(data, listType))
        }
        return levelList
    }

    override fun saveGameData(context: Context, level: Level) {
        val levelList = loadGameData(context)
        levelList[level.id - 1] = level
        saveGameData(context, levelList)
    }

    override fun saveGameData(context: Context, levelList: ArrayList<Level>) {
        val data: String = Gson().toJson(levelList)
        sharedPreferencesUtils.setString(context, CommonConstants.KEY_GAME_DATA, data)
    }

    // id bắt đầu từ 1 nhé.
    override fun loadGameData(context: Context, id: Int): Level {
        return loadGameData(context)[id - 1]
    }

    override fun loadBoomNumber(context: Context): Int {
        val boom: Int = sharedPreferencesUtils.getInt(context, CommonConstants.KEY_BOOM_DATA)
        if (boom == -1) return CommonConstants.DEAULT_BOOM
        return boom
    }

    override fun saveBoomNumber(context: Context, boom: Int) {
        sharedPreferencesUtils.setInt(context, CommonConstants.KEY_BOOM_DATA, boom)
    }

    override fun loadCoin(context: Context): Int {
        val coin = sharedPreferencesUtils.getInt(context, CommonConstants.KEY_COIN_DATA)
        if (coin == -1) return 0
        return coin
    }

    override fun saveCoin(context: Context, coin: Int) {
        sharedPreferencesUtils.setInt(context, CommonConstants.KEY_COIN_DATA, coin)
    }
}