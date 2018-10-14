package com.quangtd.qsokoban.mvpbase

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by QuangTD
 * on 3/7/2018.
 */
abstract class BaseViewHolder<in M : BaseModel>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindData(t: M)
}