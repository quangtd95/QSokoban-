package com.quangtd.qmazes.ui.screen.level

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quangtd.qmazes.R
import com.quangtd.qmazes.data.model.Level
import com.quangtd.qmazes.game.enums.GameKind
import com.quangtd.qmazes.mvpbase.BaseAdapter
import com.quangtd.qmazes.mvpbase.BaseViewHolder
import kotlinx.android.synthetic.main.item_level.view.*

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
            if (t.isUnLocked) {
                itemView.ivLocked.visibility = View.INVISIBLE
                itemView.tvLevel.visibility = View.VISIBLE
                itemView.tvLevel.text = t.id.toString()
                if (t.isComplete) {
                    when (t.gameKind){
                        GameKind.ICE->itemView.rlLevel.background = mContext.resources.getDrawable(R.drawable.round_corner_ice_bg)
                        GameKind.CLASSIC->itemView.rlLevel.background = mContext.resources.getDrawable(R.drawable.round_corner_classic_bg)
                        GameKind.DARKNESS->itemView.rlLevel.background = mContext.resources.getDrawable(R.drawable.round_corner_dark_bg)
                        GameKind.TRAP->itemView.rlLevel.background = mContext.resources.getDrawable(R.drawable.round_corner_trap_bg)
                        GameKind.TIME_TRIAL->itemView.rlLevel.background = mContext.resources.getDrawable(R.drawable.round_corner_time_bg)

                        else->itemView.rlLevel.background = mContext.resources.getDrawable(R.drawable.round_corner_yellow_bg)
                    }

                } else {
                    itemView.rlLevel.background = mContext.resources.getDrawable(R.drawable.round_corner_white_bg)
                }
            } else {
                itemView.ivLocked.visibility = View.VISIBLE
                itemView.tvLevel.visibility = View.INVISIBLE
                itemView.rlLevel.background = mContext.resources.getDrawable(R.drawable.round_corner_white_bg)
            }

            itemView.rlLevel.setOnClickListener {
                onLevelClickListener?.onClickLevel(t)
            }
        }

    }

    interface OnLevelClickListener {
        fun onClickLevel(level: Level)
    }
}