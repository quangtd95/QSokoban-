package com.quangtd.qsokoban.domain.model

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.quangtd.qsokoban.domain.game.gamemanager.ImageManager
import com.quangtd.qsokoban.util.LoadImageUtils

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class Wall(x: Int, y: Int) : Sprite(x, y) {
    private val imageManager = ImageManager.getInstance()
    private var matrix: Matrix = Matrix()
    override fun update() {

    }

    override fun draw(canvas: Canvas, paint: Paint) {
        matrix.reset()
        matrix.setScale(widthCell / imageManager.wall.width, widthCell / imageManager.wall.height)
        matrix.postTranslate(widthCell * x, widthCell * y)
        canvas.drawBitmap(imageManager.wall, matrix, paint)
    }
}