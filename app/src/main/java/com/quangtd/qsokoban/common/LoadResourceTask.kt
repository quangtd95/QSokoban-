package com.quangtd.qsokoban.common

import android.content.Context
import android.os.AsyncTask
import com.quangtd.qsokoban.domain.game.gamemanager.ImageManager

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class LoadResourceTask(var context: Context, var asyncTaskCallback: AsyncTaskCallback<Float, Void?>) : AsyncTask<Void, Float, Void?>() {
    override fun doInBackground(vararg params: Void?): Void? {
        val imageManager = ImageManager.getInstance()
        imageManager.initResource(context)
        return null
    }



}