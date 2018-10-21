package com.quangtd.qsokoban.ui.screen.level

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.quangtd.qsokoban.R
import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.mvpbase.BaseActivity
import com.quangtd.qsokoban.ui.screen.game.GameActivity
import com.quangtd.qsokoban.util.DialogUtils
import com.quangtd.qsokoban.util.RecyclerViewUtils
import kotlinx.android.synthetic.main.activity_level.*

/**
 * Created by quang.td95@gmail.com
 * on 10/20/2018.
 */
class LevelActivity : BaseActivity<LevelView, LevelPresenter>(), LevelView, LevelAdapter.OnLevelClickListener {

    private lateinit var levelAdapter: LevelAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        initValues()
        initViews()
        initAction()
    }

    private fun initAction() {
        levelAdapter.onLevelClickListener = this
        getPresenter(this).setAdapter(levelAdapter)
        getPresenter(this).loadData()
    }

    override fun scrollToCurrentGame(position: Int) {
        rvLevel.post {
            rvLevel.scrollToPosition(position)
        }
    }


    private fun initViews() {
        RecyclerViewUtils.getInstance().setUpGridVertical(this, rvLevel, 4)
    }

    private fun initValues() {
        levelAdapter = LevelAdapter(this)
        rvLevel.adapter = levelAdapter
    }

    override fun onClickLevel(level: Level) {
        getPresenter(this).chooseLevel(level)
    }

    override fun onLoadedData() {
        levelAdapter.refresh()
    }

    override fun showAlertLevelNotUnlock() {
        DialogUtils.showUnlockAlert(this)
    }

    override fun startGameScreen(level: Level) {
        GameActivity.startGameActivity(this, level)
        finish()
    }

    companion object {
        fun startLevelActivity(context: Context) {
            context.startActivity(Intent(context, LevelActivity::class.java))
        }
    }
}