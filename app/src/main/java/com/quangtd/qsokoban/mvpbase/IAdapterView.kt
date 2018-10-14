package com.quangtd.qsokoban.mvpbase

/**
 * Created by QuangTD
 * on 1/18/2018.
 */

interface IAdapterView {
    fun refresh()

    fun refreshInsert(position: Int) {}

    fun refreshRemove(position: Int) {}

    fun refreshChanged(position: Int) {}

    fun refreshAdd() {}
}
