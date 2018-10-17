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
    private var completeFlg = false
    var nextPoint = Point()

    override fun update() {
        completeFlg = false
        map.destList.forEach { dest ->
            if (dest.x == x && dest.y == y) completeFlg = true
        }
    }

    public fun isDone(): Boolean {
        return completeFlg
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
        nextPoint = checkPoint
        return true
    }

    public fun saveNewLocation() {
        this.x = nextPoint.x
        this.y = nextPoint.y
        this.xF = x.toFloat()
        this.yF = y.toFloat()
    }
}