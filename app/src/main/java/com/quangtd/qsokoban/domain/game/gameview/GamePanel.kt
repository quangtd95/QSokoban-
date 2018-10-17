package com.quangtd.qsokoban.domain.game.gameview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import com.quangtd.qsokoban.domain.game.enums.RenderState
import com.quangtd.qsokoban.domain.game.gamemanager.GameManager
import com.quangtd.qsokoban.domain.model.SokobanMap
import com.quangtd.qsokoban.util.ScreenUtils

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class GamePanel(var context: Context,
                var gameManager: GameManager,
                private var viewHolder: SurfaceHolder) {
    private lateinit var map: SokobanMap
    private lateinit var canvas: Canvas
    private lateinit var paintPlayer: Paint
    private lateinit var paintBackground: Paint
    private lateinit var paintGround: Paint
    private lateinit var paintWall: Paint
    private lateinit var paintDest: Paint
    private lateinit var paintBox: Paint
    private var widthScreen: Int = 0

    fun loadGameUI() {
        map = gameManager.getSokobanMap()
        paintPlayer = Paint(Paint.FILTER_BITMAP_FLAG)
        paintBackground = Paint(Paint.FILTER_BITMAP_FLAG)
        paintGround = Paint(Paint.FILTER_BITMAP_FLAG)
        paintWall = Paint(Paint.FILTER_BITMAP_FLAG)
        paintDest = Paint(Paint.FILTER_BITMAP_FLAG)
        paintBox = Paint(Paint.FILTER_BITMAP_FLAG)
        widthScreen = ScreenUtils.getWidthScreen(context)

    }

    fun draw() {
        try {
            canvas = viewHolder.lockCanvas()
            draw(canvas)
            viewHolder.unlockCanvasAndPost(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun draw(canvas: Canvas) {
        canvas.save()
        canvas.translate((widthScreen - map.cols * map.widthCell) / 2, map.widthCell)
        drawMap(canvas)
        drawPlayer(canvas)
        canvas.restore()
    }

    private fun drawMap(canvas: Canvas) {
        drawBackground(canvas)
        drawGround(canvas)
        drawWall(canvas)
        drawDest(canvas)
        drawBox(canvas)
    }

    private fun drawDest(canvas: Canvas) {
        map.destList.forEach { dest ->
            dest.draw(canvas, paintDest)
        }
    }

    private fun drawBox(canvas: Canvas) {
        map.boxList.forEach { box ->
            box.draw(canvas, paintBox)
        }
    }

    private fun drawWall(canvas: Canvas) {
        map.wallList.forEach { wall ->
            wall.draw(canvas, paintWall)
        }
    }

    private fun drawGround(canvas: Canvas) {
        map.groundList.forEach { ground ->
            ground.draw(canvas, paintGround)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
    }

    private fun drawPlayer(canvas: Canvas) {
        map.player.draw(canvas, paintPlayer)
    }

    fun bindRenderCalBack(renderCallback: RenderState.RenderCallback) {

    }
}