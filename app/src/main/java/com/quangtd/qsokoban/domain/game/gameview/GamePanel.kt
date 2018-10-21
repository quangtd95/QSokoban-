package com.quangtd.qsokoban.domain.game.gameview

import android.content.Context
import android.graphics.*
import android.view.SurfaceHolder
import com.quangtd.qsokoban.R
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
    private var colorBackground: Int = 0
    protected var tempBackgroundBitmap: Bitmap? = null
    private var userBoom = false
    private var marginLeft = 0F
    private var marginTop = 0F

    fun loadGameUI() {
        map = gameManager.getSokobanMap()
        paintPlayer = Paint(Paint.FILTER_BITMAP_FLAG)
        paintBackground = Paint(Paint.FILTER_BITMAP_FLAG)
        paintGround = Paint(Paint.FILTER_BITMAP_FLAG)
        paintWall = Paint(Paint.FILTER_BITMAP_FLAG)
        paintDest = Paint(Paint.FILTER_BITMAP_FLAG)
        paintBox = Paint(Paint.FILTER_BITMAP_FLAG)
        widthScreen = ScreenUtils.getWidthScreen(context)
        colorBackground = context.resources.getColor(R.color.bgGame)

    }

    fun draw() {
        try {
            canvas = viewHolder.lockCanvas()
            draw(canvas)
            viewHolder.unlockCanvasAndPost(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                viewHolder.unlockCanvasAndPost(canvas)
            } catch (e1: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun draw(canvas: Canvas) {
        canvas.save()
        drawBackground(canvas)
        marginLeft = (widthScreen - map.cols * map.widthCell) / 2
        marginTop = map.widthCell
        canvas.translate(marginLeft, marginTop)
        if (tempBackgroundBitmap == null) {
            tempBackgroundBitmap = Bitmap.createBitmap((widthScreen), (getHeight()), Bitmap.Config.ARGB_8888)
            val tempCanvasBackground = Canvas(tempBackgroundBitmap)
            drawMap(tempCanvasBackground)
        } else {
            canvas.drawBitmap(tempBackgroundBitmap, 0F, 0F, paintBackground)
        }
        drawWall(canvas)
        drawDest(canvas)
        drawBox(canvas)
        drawPlayer(canvas)
        if (userBoom) {
            drawBoom(canvas)
        }
    }

    private fun drawBoom(canvas: Canvas) {
        gameManager.boom?.draw(canvas, paintWall)
    }

    private fun drawMap(canvas: Canvas) {
        drawGround(canvas)
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
        canvas.drawColor(colorBackground)
    }

    private fun drawPlayer(canvas: Canvas) {
        map.player.draw(canvas, paintPlayer)
    }

    fun bindRenderCalBack(renderCallback: RenderState.RenderCallback) {

    }

    fun getHeight(): Int {
        return (map.widthCell * (map.rows + 2)).toInt()
    }

    fun userBoom(b: Boolean) {
        userBoom = b
    }

    fun convertPxToPoint(x: Float, y: Float): Point? {
        val xF = (x - marginLeft) / map.widthCell
        val yF = (y - marginTop) / map.widthCell
        if (xF < 0 || xF > map.cols) return null
        if (yF < 0 || yF > map.rows) return null
        return Point(Math.floor(xF.toDouble()).toInt(), Math.floor(yF.toDouble()).toInt())
    }
}