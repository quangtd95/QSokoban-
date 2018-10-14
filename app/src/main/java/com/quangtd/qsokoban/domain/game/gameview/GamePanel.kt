package com.quangtd.qsokoban.domain.game.gameview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import com.quangtd.qsokoban.domain.game.gamemanager.GameManager
import com.quangtd.qsokoban.domain.model.SokobanMap
import java.util.*

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class GamePanel(var context: Context,
                var gameManager: GameManager,
                var viewHolder: SurfaceHolder) {
    private lateinit var map: SokobanMap
    private lateinit var canvas: Canvas
    private lateinit var paint: Paint
    private var widthScreen: Int = 0

    fun loadGameUI() {
        map = gameManager.getSokobanMap()
        paint = Paint(Paint.FILTER_BITMAP_FLAG)
    }

    fun draw() {
        try {
            canvas = viewHolder.lockCanvas()
            canvas.drawColor(Color.WHITE)
            map.player.draw(canvas, paint)
            viewHolder.unlockCanvasAndPost(canvas)
        } catch (e: Exception) {
            viewHolder.unlockCanvasAndPost(canvas)
            e.printStackTrace()
        }
    }
}