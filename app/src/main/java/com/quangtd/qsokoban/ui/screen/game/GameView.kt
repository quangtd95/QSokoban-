package com.quangtd.qsokoban.ui.screen.game

import android.view.SurfaceHolder
import com.quangtd.qsokoban.mvpbase.IBaseView

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
interface GameView : IBaseView {
    fun getSurfaceHolder(): SurfaceHolder
}