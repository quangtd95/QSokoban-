package com.quangtd.qsokoban.domain.model

import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Point
import com.quangtd.qsokoban.domain.game.enums.GameDirection
import com.quangtd.qsokoban.domain.game.gamemanager.ImageManager
import com.quangtd.qsokoban.util.LoadImageUtils

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class Box(x: Int, y: Int) : Sprite(x, y) {
    private val matrix = Matrix()
    lateinit var map: SokobanMap
    private val imageManager = ImageManager.getInstance()
    override fun update() {
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        LoadImageUtils.setupMatrixFitCell(matrix, (widthCell * 0.9F).toInt(), (widthCell * 0.9F).toInt(), imageManager.box.width, imageManager.box.height, widthCell, xF, yF)
        canvas.drawBitmap(imageManager.box, matrix, paint)
    }

    fun canPush(direction: GameDirection): Boolean {
        val checkPoint = Point()
        when (direction) {
            GameDirection.LEFT -> {
                checkPoint.x = x - 1
                checkPoint.y = y
            }
            GameDirection.RIGHT -> {
                checkPoint.x = x + 1
                checkPoint.y = y
            }
            GameDirection.UP -> {
                checkPoint.x = x
                checkPoint.y = y - 1
            }
            GameDirection.DOWN -> {
                checkPoint.x = x
                checkPoint.y = y + 1
            }
            GameDirection.STOP -> {
                return false
            }
        }
        map.boxList.forEach { it ->
            if (it.x == checkPoint.x && it.y == checkPoint.y) return false
        }
        map.wallList.forEach { it ->
            if (it.x == checkPoint.x && it.y == checkPoint.y) return false
        }
        return true


    }
}