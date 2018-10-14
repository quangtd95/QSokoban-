package com.quangtd.qsokoban.mvpbase

import android.content.Context

/**
 * Created by QuangTD
 * on 1/12/2018.
 */
abstract class BasePresenter<V : IBaseView> {
    private var iView: V? = null

    open fun getIView(): V? {
        return iView
    }

    fun attachView(v: V) {
        iView = v
    }

    fun detachView() {
        iView = null
    }

    open fun onDestroy() {

    }

    fun getContext(): Context? {
        return this.iView?.getViewContext()
    }

    abstract fun onInit()

}