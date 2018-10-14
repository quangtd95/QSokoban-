package com.quangtd.qsokoban.domain.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.quangtd.qsokoban.domain.game.gamemanager.ImageManager
import com.quangtd.qsokoban.util.LogUtils

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class Player(x: Int = 0, y: Int = 0,
             var map: SokobanMap) : Sprite(x, y) {
    private var animation = Animation()

    init {
        animation.setFrames(ImageManager.getInstance().playerLeft, 7)
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(animation.image, Matrix(), paint)
    }

    override fun update() {
        LogUtils.e("$x - $y $widthCell")
        animation.update()
    }
}