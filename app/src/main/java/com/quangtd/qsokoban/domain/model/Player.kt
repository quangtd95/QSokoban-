package com.quangtd.qsokoban.domain.model

import android.graphics.*
import com.quangtd.qsokoban.domain.game.enums.GameDirection
import com.quangtd.qsokoban.domain.game.gamemanager.ImageManager

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class Player(x: Int = 0, y: Int = 0,
             var map: SokobanMap) : Sprite(x, y) {
    private val imageManager = ImageManager.getInstance()
    private var animation = Animation()
    private var direction: GameDirection = GameDirection.STOP
    private val matrix = Matrix()
    private var pushedBox: Box? = null
    private var isMoving = false
    private var nextDest = Point()
    private var velocity = 0.1F

    init {
        animation.setFrames(imageManager.playerIdleDown, 7)
        nextDest.x = x
        nextDest.y = y
        xF = x.toFloat()
        yF = y.toFloat()
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        matrix.reset()
        matrix.setScale(widthCell / animation.image.width, widthCell / animation.image.height)
        matrix.postTranslate(widthCell * xF, widthCell * yF)
        canvas.drawBitmap(animation.image, matrix, paint)
    }

    override fun update() {
        animation.update()
        if (isMoving) {
            when (direction) {
                GameDirection.LEFT -> {
                    xF -= velocity
                    pushedBox?.xF = pushedBox?.xF?.minus(velocity)!!
                    if (xF <= nextDest.x) {
                        xF = nextDest.x.toFloat()
                        direction = GameDirection.STOP
                        animation.setFrames(imageManager.playerIdleLeft)
                    }
                }
                GameDirection.RIGHT -> {
                    xF += velocity
                    pushedBox?.xF = pushedBox?.xF?.plus(velocity)!!
                    if (xF >= nextDest.x) {
                        xF = nextDest.x.toFloat()
                        direction = GameDirection.STOP
                        animation.setFrames(imageManager.playerIdleRight)
                    }
                }
                GameDirection.UP -> {
                    yF -= velocity
                    pushedBox?.yF = pushedBox?.yF?.minus(velocity)!!
                    if (yF <= nextDest.y) {
                        yF = nextDest.y.toFloat()
                        direction = GameDirection.STOP
                        animation.setFrames(imageManager.playerIdleUp)
                    }
                }
                GameDirection.DOWN -> {
                    yF += velocity
                    pushedBox?.yF = pushedBox?.yF?.plus(velocity)!!
                    if (yF >= nextDest.y) {
                        yF = nextDest.y.toFloat()
                        direction = GameDirection.STOP
                        animation.setFrames(imageManager.playerIdleDown)
                    }
                }
                GameDirection.STOP -> {
                    isMoving = false
                    xF = nextDest.x.toFloat()
                    yF = nextDest.y.toFloat()
                    x = nextDest.x
                    y = nextDest.y
                }
            }
        }
    }

    fun move(direction: GameDirection) {
        if (!isMoving) {
            isMoving = true
            xF = x.toFloat()
            yF = y.toFloat()
            when (direction) {
                GameDirection.LEFT -> {
                    nextDest.x = x - 1
                    nextDest.y = y
                    animation.setFrames(imageManager.playerLeft)
                    if (!checkCollisionWall(nextDest)) {
                        this.direction = direction
                    } else {
                        clearNextDest()
                    }
                }
                GameDirection.RIGHT -> {
                    nextDest.x = x + 1
                    nextDest.y = y
                    animation.setFrames(imageManager.playerRight)
                    if (!checkCollisionWall(nextDest)) {
                        this.direction = direction
                    } else {
                        clearNextDest()
                    }
                }
                GameDirection.UP -> {
                    nextDest.x = x
                    nextDest.y = y - 1
                    animation.setFrames(imageManager.playerUp)
                    if (!checkCollisionWall(nextDest)) {
                        this.direction = direction
                    } else {
                        clearNextDest()
                    }
                }
                GameDirection.DOWN -> {
                    nextDest.x = x
                    nextDest.y = y + 1
                    animation.setFrames(imageManager.playerDown)
                    if (!checkCollisionWall(nextDest)) {
                        this.direction = direction
                    } else {
                        clearNextDest()
                    }
                }
                GameDirection.STOP -> {

                }
            }
            pushedBox = getBox(nextDest)
        }
    }

    private fun clearNextDest() {
        nextDest.x = x
        nextDest.y = y
        xF = x.toFloat()
        yF = y.toFloat()
    }

    /**
     * true nếu bước tiếp theo là tường
     */
    private fun checkCollisionWall(nextDest: Point): Boolean {
        map.wallList.forEach { wall ->
            if (nextDest.x == wall.x && nextDest.y == wall.y) {
                return true
            }
        }
        return false
    }

    private fun getBox(nextDest: Point): Box? {
        return map.boxList.find { it.x == nextDest.x && it.y == nextDest.y }
    }
}