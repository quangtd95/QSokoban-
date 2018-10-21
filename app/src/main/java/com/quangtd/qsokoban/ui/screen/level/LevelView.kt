package com.quangtd.qsokoban.ui.screen.level

import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.mvpbase.IBaseView

/**
 * Created by quang.td95@gmail.com
 * on 10/20/2018.
 */
interface LevelView : IBaseView {
    fun onLoadedData()
    fun showAlertLevelNotUnlock()
    fun startGameScreen(level: Level)
    fun scrollToCurrentGame(position: Int)
}