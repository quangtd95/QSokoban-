package com.quangtd.qsokoban.ui.screen.level

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quangtd.qsokoban.R
import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.mvpbase.BaseAdapter
import com.quangtd.qsokoban.mvpbase.BaseViewHolder
import kotlinx.android.synthetic.main.item_level.view.*
import java.util.*

/**
 * Created by quang.td95@gmail.com
 * on 9/2/2018.
 */
class LevelAdapter(private var mContext: Context) : BaseAdapter<Level, LevelAdapter.LevelViewHolder>() {
    var onLevelClickListener: OnLevelClickListener? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LevelViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_level, p0, false)
        return LevelViewHolder(view)
    }

    inner class LevelViewHolder(itemView: View) : BaseViewHolder<Level>(itemView) {

        override fun bindData(t: Level) {
            //đã hoàn thành
            when {
                t.isComplete -> itemView.imbBox.setImageResource(R.drawable.crate_yellow)
                //mới được mở khóa
                t.isUnlock -> itemView.imbBox.setImageResource(R.drawable.crate_blue)
                //chưa mở khóa
                else -> itemView.imbBox.setImageResource(R.drawable.crate_beige)
            }
            itemView.ratingBar.rating = t.ranking.toFloat()
            itemView.lbLevel.text = t.id.toString()
            itemView.lbLevel.setOnClickListener {
                onLevelClickListener?.onClickLevel(t)
            }
        }

    }

    interface OnLevelClickListener {
        fun onClickLevel(level: Level)
    }
}