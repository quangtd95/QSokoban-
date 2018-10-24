package com.quangtd.qsokoban.ui.screen.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GestureDetectorCompat
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.quangtd.qsokoban.R
import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.mvpbase.BaseActivity
import com.quangtd.qsokoban.ui.component.OnSwipeListener
import com.quangtd.qsokoban.ui.screen.level.LevelActivity
import com.quangtd.qsokoban.util.DialogUtils
import com.quangtd.qsokoban.util.LogUtils
import com.quangtd.qsokoban.util.ScreenUtils
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : BaseActivity<GameView, GamePresenter>(), GameView, SurfaceHolder.Callback {

    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var level: Level
    private var moveStep: Int = 0
    private var target: Int = 0
    private var highScore: Int = 0
    private var ranking: Int = 3
    private var best: Int = 0
    private var chooseBoomFlg = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initValues()
        initViews()
        initAction()
    }

    private fun initValues() {
        level = intent.extras.getSerializable("level") as Level
        moveStep = 0
        highScore = level.savedMove
        best = level.savedMove
        ratingBar.rating = ranking.toFloat()

    }

    private fun initViews() {
        txtMove.text = moveStep.toString()
        txtTarget.text = target.toString()
        txtLevel.text = String.format(getString(R.string.level_string), level.id)
        txtBest.text = best.toString()
        if (level.isComplete) {
            imbNext.visibility = View.VISIBLE
        } else {
            imbNext.visibility = View.GONE
        }
        llTool.visibility = View.VISIBLE
        llUserBoom.visibility = View.INVISIBLE
    }

    private fun initAction() {
        sokobanView.getSurfaceHolder().addCallback(this)
        mDetector = GestureDetectorCompat(this, object : OnSwipeListener() {
            override fun onSwipe(direction: Direction): Boolean {
                if (getPresenter(this@GameActivity).isWin() || chooseBoomFlg) return false
                getPresenter(this@GameActivity).move(direction)
                return true
            }

            override fun onDown(e: MotionEvent?): Boolean {
                if (e == null) return false
                if (!chooseBoomFlg) return false
                getPresenter(this@GameActivity).setBoomPosition(e.x, e.y - groupTop.height - txtLevel.height)
                return true
            }
        })
        imbBoom.setOnClickListener {
            if (!getPresenter(this).isWin()) {
                getPresenter(this).useBoom()
            }
        }
        imbGate.setOnClickListener { }
        imbHint.setOnClickListener { }
        imbNext.setOnClickListener {
            getPresenter(this).moveNextLevel()
        }
        imbReload.setOnClickListener {
            getPresenter(this).pauseGame()
            DialogUtils.showReload(this, {
                chooseWallToDestroy(false)
                getPresenter(this).reloadGame()
                getPresenter(this).resumeGame()
            }, {
                getPresenter(this).resumeGame()
            })

        }
        imbPause.setOnClickListener { onBackPressed() }
        btnCancelBoom.setOnClickListener {
            getPresenter(this).cancelUseBoom()
        }
        btnUseBoom.setOnClickListener {
            getPresenter(this).destroyWall()
        }
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
            target = getPresenter(this).getTarget()
            txtTarget.text = target.toString()
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
        DialogUtils.showExitConfirm(this@GameActivity, {
            LevelActivity.startLevelActivity(this@GameActivity)
            finish()
        }, {
            getPresenter(this@GameActivity).resumeGame()
        })

    }

    override fun onDestroy() {
        getPresenter(this).stopGame()
        super.onDestroy()
    }

    override fun showWinGameAlert() {
        runOnUiThread {
            uiChangeListener()
            imbNext.visibility = View.VISIBLE
            DialogUtils.showWin(this, getString(R.string.you_win)) {
                getPresenter(this).moveNextLevel()
            }
        }
    }

    override fun showWinGameBonusCoinAlert(coinBonus: Int) {
        runOnUiThread {
            uiChangeListener()
            imbNext.visibility = View.VISIBLE
            DialogUtils.showWinBonusCoin(this, getString(R.string.you_win), coinBonus) {
                getPresenter(this).moveNextLevel()
            }
        }
    }

    override fun moveNextLevel(nextLevel: Level) {
        GameActivity.startGameActivity(this, nextLevel)
        finish()
    }

    override fun setBest(savedMove: Int) {
        txtBest.text = savedMove.toString()
    }

    override fun showLoseGameAlert() {
        runOnUiThread {
            getPresenter(this).pauseGame()
            uiChangeListener()
            DialogUtils.showError(this, getString(R.string.you_loose)) {}
        }
    }

    override fun setMoved(moveStep: Int, ranking: Int) {
        txtMove.text = moveStep.toString()
        ratingBar.rating = ranking.toFloat()
    }

    override fun setHeightGame(height: Int) {
        val sfHeight = getSurfaceHeight()
        val params = sokobanView.layoutParams as ViewGroup.MarginLayoutParams
        params.height = height
        sokobanView.layoutParams = params
        sokobanView.invalidate()
        /*if (margin > AdSize.BANNER.height) {
            initAdMod()
        } else {
            LogUtils.e("size not compatible : $margin and ${AdSize.BANNER.height}")
        }*/
    }

    override fun getSurfaceHeight(): Int {
        return ScreenUtils.getHeightScreen(this) - groupTop.height - txtLevel.height
    }

    override fun setBoomNumber(loadBoomNumber: Int) {
        runOnUiThread {
            txtBoomCount.text = loadBoomNumber.toString()
        }
    }

    override fun setCoinNumber(loadCoin: Int) {
        runOnUiThread {
            txtCoin.text = loadCoin.toString()
        }
    }

    override fun notEnoughBoomAlert() {
        runOnUiThread {
            DialogUtils.showNotEnoughBoomAlert(this)
        }
    }

    override fun showRewardBoom() {
        runOnUiThread {
            DialogUtils.showRewardBoom(this)
        }
    }

    override fun chooseWallToDestroy(b: Boolean) {
        runOnUiThread {
            chooseBoomFlg = b
            llTool.visibility = if (b) View.INVISIBLE else View.VISIBLE
            llUserBoom.visibility = if (b) View.VISIBLE else View.INVISIBLE
            if (b) {
                getPresenter(this).resumeGame()
            }
        }
    }

    override fun placeBoomToDestroyAlert() {
        DialogUtils.showPlaceBoomToDestroy(this)
    }

    companion object {
        fun startGameActivity(context: Context, level: Level) {
            val intent = Intent(context, GameActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("level", level)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
