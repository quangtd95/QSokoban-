package com.quangtd.qsokoban.domain.model

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import com.quangtd.qsokoban.domain.game.gamemanager.ImageManager

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class Destination(x: Int, y: Int) : Sprite(x, y) {
    private val matrix = Matrix()
    private val imageManager = ImageManager.getInstance()
    var rect = RectF()

    override fun update() {
        rect.set(xF * widthCell, yF * widthCell, (xF + 1) * widthCell, (yF+ 1) * widthCell)
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        matrix.reset()
        val newWidth = widthCell / 2
        val newHeight = widthCell / 2
        matrix.setScale(newWidth / imageManager.destination.width, newHeight / imageManager.destination.height)
        matrix.postTranslate(widthCell * x + widthCell / 2 - newWidth / 2, widthCell * y + widthCell / 2 - newHeight / 2)
        canvas.drawBitmap(imageManager.destination, matrix, paint)
    }

}