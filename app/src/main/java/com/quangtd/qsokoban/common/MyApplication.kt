package com.quangtd.qsokoban.common

import android.app.Application
import com.quangtd.qsokoban.domain.game.gamemanager.ImageManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
//        var task = LoadResourceTask(this, this)
        ImageManager.getInstance().initResource(this)

    }

}