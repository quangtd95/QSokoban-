package com.quangtd.qsokoban.domain.model

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

/**
 * Created by quang.td95@gmail.com
 * on 9/2/2018.
 */
abstract class Sprite(
        protected var x: Int = 0,
        protected var y: Int = 0,
        protected var xF: Float = 0F,
        protected var yF: Float = 0F
) {
    init {
        xF = x.toFloat()
        yF = y.toFloat()
    }

    var widthCell: Float = 0F
    abstract fun update()
    abstract fun draw(canvas: Canvas, paint: Paint)

    fun calcDistance(p1: PointF, p2: PointF): Float {
        return Math.sqrt(Math.pow(((p2.x - p1.x).toDouble()), 2.0) + Math.pow(((p2.y - p1.y).toDouble()), 2.0)).toFloat()
    }
}
