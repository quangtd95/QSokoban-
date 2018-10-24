package com.quangtd.qsokoban.ui.screen.game

import android.view.SurfaceHolder
import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.mvpbase.IBaseView

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
interface GameView : IBaseView {
    fun getSurfaceHolder(): SurfaceHolder
    fun showWinGameAlert()
    fun showLoseGameAlert()
    fun setMoved(moveStep: Int, ranking: Int)
    fun getSurfaceHeight(): Int
    fun setHeightGame(height: Int)
    fun moveNextLevel(nextLevel: Level)
    fun setBest(savedMove: Int)
    fun chooseWallToDestroy(b: Boolean)
    fun placeBoomToDestroyAlert()
    fun showWinGameBonusCoinAlert(coinBonus: Int)
    fun setBoomNumber(loadBoomNumber: Int)
    fun setCoinNumber(loadCoin: Int)
    fun notEnoughBoomAlert()
    fun showRewardBoom()
}