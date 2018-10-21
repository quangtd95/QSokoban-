package com.quangtd.qsokoban.domain.game.gamemanager

import android.content.Context
import android.graphics.Bitmap
import com.quangtd.qsokoban.R
import com.quangtd.qsokoban.util.LoadImageUtils

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class ImageManager private constructor() {
    companion object {
        private var ins: ImageManager? = null

        fun getInstance(): ImageManager {
            if (ins == null) {
                ins = ImageManager()
            }
            return ins!!
        }
    }

    lateinit var ground: Bitmap
    lateinit var box: Bitmap
    lateinit var boxFinish: Bitmap
    lateinit var wall: Bitmap
    lateinit var destination: Bitmap
    lateinit var boom: Bitmap
    var playerIdleLeft: ArrayList<Bitmap> = ArrayList()
    var playerIdleRight: ArrayList<Bitmap> = ArrayList()
    var playerIdleUp: ArrayList<Bitmap> = ArrayList()
    var playerIdleDown: ArrayList<Bitmap> = ArrayList()
    var playerLeft: ArrayList<Bitmap> = ArrayList()
    var playerRight: ArrayList<Bitmap> = ArrayList()
    var playerUp: ArrayList<Bitmap> = ArrayList()
    var playerDown: ArrayList<Bitmap> = ArrayList()
    var boomExplode: ArrayList<Bitmap> = ArrayList()

    fun initResource(context: Context) {
        boom = LoadImageUtils.loadImage(context, R.drawable.boom)
        box = LoadImageUtils.loadImage(context, R.drawable.cratedark_blue)
        boxFinish = LoadImageUtils.loadImage(context, R.drawable.cratedark_yellow)
        wall = LoadImageUtils.loadImage(context, R.drawable.wall_beige)
        destination = LoadImageUtils.loadImage(context, R.drawable.endpoint_blue)
        playerIdleLeft.add(LoadImageUtils.loadImage(context, R.drawable.character_1))
        playerIdleRight.add(LoadImageUtils.loadImage(context, R.drawable.character_2))
        playerIdleUp.add(LoadImageUtils.loadImage(context, R.drawable.character_7))
        playerIdleDown.add(LoadImageUtils.loadImage(context, R.drawable.character_4))
        playerLeft.add(playerIdleLeft.first())
        playerLeft.add(LoadImageUtils.loadImage(context, R.drawable.character_10))
        playerRight.add(playerIdleRight.first())
        playerRight.add(LoadImageUtils.loadImage(context, R.drawable.character_3))
        playerUp.add(playerIdleUp.first())
        playerUp.add(LoadImageUtils.loadImage(context, R.drawable.character_8))
        playerUp.add(LoadImageUtils.loadImage(context, R.drawable.character_9))
        playerDown.add(playerIdleDown.first())
        playerDown.add(LoadImageUtils.loadImage(context, R.drawable.character_5))
        playerDown.add(LoadImageUtils.loadImage(context, R.drawable.character_6))
        ground = LoadImageUtils.loadImage(context, R.drawable.groundgravel_concrete)
        boomExplode = LoadImageUtils.loadSubImage(context, R.drawable.explode, 1, 5)
    }
}