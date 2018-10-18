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

    override fun loadData(context: Context): ArrayList<Level> {
        //khởi tạo list các level
        val levelList = ArrayList<Level>()
        val data = sharedPreferencesUtils.getString(context, CommonConstants.KEY_GAME_DATA)
        //nếu data = null hoặc empty -> load lần đầu. set data rồi lưu luôn.
        if (TextUtils.isEmpty(data)) {
            for (i in 1..1000) {
                val l = Level(id = i, gameKind = GameKind.CLASSIC.value, isComplete = false, isUnlock = (i == 1), savedMove = 0, ranking = 0)
                levelList.add(l)
            }
            saveData(context, levelList)
        } else {
            val listType: Type = object : TypeToken<ArrayList<Level>>() {}.type
            levelList.addAll(Gson().fromJson(data, listType))
        }
        return levelList
    }

    override fun saveData(context: Context, level: Level) {
        val levelList = loadData(context)
        levelList[level.id - 1] = level
        saveData(context, levelList)
    }

    override fun saveData(context: Context, levelList: ArrayList<Level>) {
        val data: String = Gson().toJson(levelList)
        sharedPreferencesUtils.setString(context, CommonConstants.KEY_GAME_DATA, data)
    }

    // id bắt đầu từ 1 nhé.
    override fun loadData(context: Context, id: Int): Level {
        return loadData(context)[id - 1]
    }
}