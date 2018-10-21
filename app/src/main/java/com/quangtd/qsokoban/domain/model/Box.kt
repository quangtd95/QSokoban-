package com.quangtd.qsokoban.domain.model

import android.graphics.*
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
    var rect = RectF()
    override fun update() {
        completeFlg = false
        rect.set(xF * widthCell, yF * widthCell, (xF + 1) * widthCell, (yF+ 1) * widthCell)
        map.destList.forEach { dest ->
            if (dest.x == x && dest.y == y) completeFlg = true
        }
    }

    private fun checkIntersectDest(): Boolean {
        map.destList.forEach { dest ->
            if (dest.rect.intersect(rect)) return true
        }
        return false
    }

    fun isDone(): Boolean {
        return completeFlg
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        LoadImageUtils.setupMatrixFitCell(matrix, (widthCell * 0.9F).toInt(), (widthCell * 0.9F).toInt(), imageManager.box.width, imageManager.box.height, widthCell, xF, yF)
        if (checkIntersectDest()) {
            canvas.drawBitmap(imageManager.boxFinish, matrix, paint)
        } else {
            canvas.drawBitmap(imageManager.box, matrix, paint)
        }
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