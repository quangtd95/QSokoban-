package com.quangtd.qsokoban.domain.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import com.quangtd.qsokoban.domain.game.gamemanager.ImageManager
import com.quangtd.qsokoban.util.LoadImageUtils

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class Boom(x: Int, y: Int) : Sprite(x, y) {
    private val imageManager = ImageManager.getInstance()
    private var animation = Animation()
    private var matrix: Matrix = Matrix()
    var explode: Boolean = false
    var explodeFinish: ((Unit) -> Unit)? = null


    override fun update() {
        if (explode) {
            if (animation.hasPlayed(1)) {
                explodeFinish?.invoke(Unit)
            } else {
                animation.update()
            }
        }
    }

    fun destroy(l: (Unit) -> Unit) {
        explode = true
        animation.setFrames(imageManager.boomExplode, 2)
        explodeFinish = l
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        val width: Float
        val height: Float
        if (!explode) {
            width = widthCell * 0.7F
            height = widthCell * 0.7F
        } else {
            width = widthCell
            height = widthCell
        }
        LoadImageUtils.setupMatrixFitCell(matrix, width.toInt(), height.toInt(), getImage().width, getImage().height, widthCell, x.toFloat(), y.toFloat())
        canvas.drawBitmap(getImage(), matrix, paint)
    }

    private fun getImage(): Bitmap {
        return if (explode) {
            animation.image
        } else {
            imageManager.boom
        }
    }
}