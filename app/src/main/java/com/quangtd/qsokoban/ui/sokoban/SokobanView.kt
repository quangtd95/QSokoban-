package com.quangtd.qsokoban.ui.sokoban

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * Created by quang.td95@gmail.com
 * on 9/1/2018.
 */
class SokobanView(context: Context?, attrs: AttributeSet?) : SurfaceView(context, attrs) {

    init {
        init()
    }

    private fun init() {

    }

    fun getSurfaceHolder(): SurfaceHolder {
        return holder
    }
}