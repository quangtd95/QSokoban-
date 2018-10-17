package com.quangtd.qsokoban.ui.screen.game

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import com.quangtd.qsokoban.R
import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.mvpbase.BaseActivity
import com.quangtd.qsokoban.ui.component.OnSwipeListener
import com.quangtd.qsokoban.util.DialogUtils
import com.quangtd.qsokoban.util.LogUtils
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : BaseActivity<GameView, GamePresenter>(), GameView, SurfaceHolder.Callback {

    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var level: Level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initValues()
        initViews()
        initAction()
    }

    private fun initValues() {
        level = Level(id = intent.getIntExtra("level", 1))
    }

    private fun initViews() {

    }

    private fun initAction() {
        sokobanView.getSurfaceHolder().addCallback(this)
        mDetector = GestureDetectorCompat(this, object : OnSwipeListener() {
            override fun onSwipe(direction: Direction): Boolean {
                getPresenter(this@GameActivity).move(direction)
                return true
            }
        })
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        LogUtils.e("surface Changed")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        LogUtils.e("surfaceDestroyed")
        getPresenter(this).pauseGame()
        isPause = true
    }

    private var isPause = false
    override fun surfaceCreated(p0: SurfaceHolder?) {
        LogUtils.e("surfaceCreated")
        if (!isPause) {
            getPresenter(this@GameActivity).setUpGame(level)
            /*if (getPresenter(this).canNext()) {
                next.visibility = View.VISIBLE
                next.setOnClickListener {
                    moveNextLevel()
                }
            } else {
                next.visibility = View.INVISIBLE
            }*/
        } else {
            getPresenter(this).resumeGame()
        }
    }

    override fun onStop() {
        super.onStop()
        getPresenter(this).pauseGame()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (this.mDetector.onTouchEvent(event)) {
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun getSurfaceHolder(): SurfaceHolder {
        return sokobanView.getSurfaceHolder()
    }

    override fun onBackPressed() {
        uiChangeListener()
        getPresenter(this@GameActivity).pauseGame()
//        DialogUtils.showExitConfirm(this@GameActivity, {
//            LevelActivity.startLevelActivity(this@GameActivity, Category(level!!.gameKind.id, level!!.gameKind))
//            finish()
//        }, {
//            getPresenter(this@GameActivity).resumeGame()
//        })

    }

    override fun onDestroy() {
        getPresenter(this).stopGame()
        super.onDestroy()
    }

    override fun showWinGameAlert() {
        runOnUiThread {
            uiChangeListener()
            DialogUtils.showError(this, "you win") {
                moveNextLevel()
            }
        }
    }

    private fun moveNextLevel() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("level", level.id + 1)
        startActivity(intent)
        finish()
    }

    override fun showLoseGameAlert() {
        runOnUiThread {
            getPresenter(this).pauseGame()
            uiChangeListener()
            DialogUtils.showError(this, "you loose") {}
        }
    }

}
