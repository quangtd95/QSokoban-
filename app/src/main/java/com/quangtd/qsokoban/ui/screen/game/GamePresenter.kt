package com.quangtd.qsokoban.ui.screen.game

import com.quangtd.qsokoban.domain.game.enums.GameDirection
import com.quangtd.qsokoban.domain.game.gamemanager.GameManager
import com.quangtd.qsokoban.domain.game.gameview.GamePanel
import com.quangtd.qsokoban.domain.game.thread.GameThread
import com.quangtd.qsokoban.domain.model.Level
import com.quangtd.qsokoban.domain.repository.GameDataRepository
import com.quangtd.qsokoban.domain.repository.GameDataRepositoryImpl
import com.quangtd.qsokoban.mvpbase.BasePresenter
import com.quangtd.qsokoban.ui.component.OnSwipeListener
import com.quangtd.qsokoban.util.LogUtils

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class GamePresenter : BasePresenter<GameView>() {
    private lateinit var gameManager: GameManager
    private lateinit var gamePanel: GamePanel
    private lateinit var gameThread: GameThread

    private lateinit var gameDataRepository: GameDataRepository

    override fun onInit() {
        gameDataRepository = GameDataRepositoryImpl()
    }

    fun setUpGame(level: Level) {
        var _level = gameDataRepository.loadData(getContext()!!, 1)
        gameManager = GameManager(_level)
        gameManager.loadGame(getContext()!!)
        gamePanel = GamePanel(getContext()!!, gameManager, getIView()!!.getSurfaceHolder())
        gamePanel.loadGameUI()
        gameThread = GameThread(gameManager, gamePanel)
        gameThread.start()

    }

    fun move(direction: OnSwipeListener.Direction) {
        resumeGame()
        when (direction) {
            OnSwipeListener.Direction.up -> {
                gameManager.action(GameDirection.UP)
            }
            OnSwipeListener.Direction.down -> {
                gameManager.action(GameDirection.DOWN)
            }
            OnSwipeListener.Direction.left -> {
                gameManager.action(GameDirection.LEFT)
            }
            OnSwipeListener.Direction.right -> {
                gameManager.action(GameDirection.RIGHT)
            }
        }
    }

    fun resumeGame() {
//        if (setupGameFinish) {
//            if (level.gameKind == GameKind.TIME_TRIAL) {
//                (gameManager!! as TimeTrialGameManager).playBackgroundSound()
//            }
//            gameManager!!.resetStartGameTime()
//            gameThread.renderFlg = true
//        }
    }

    fun pauseGame() {
//        if (setupGameFinish) {
//            if (level.gameKind == GameKind.TIME_TRIAL) {
//                (gameManager!! as TimeTrialGameManager).stopBackgroundSound()
//            }
//            gameThread.renderFlg = false
//        }
    }

    fun stopGame() {
//        if (level.gameKind == GameKind.TIME_TRIAL) {
//            (gameManager!! as TimeTrialGameManager).stopBackgroundSound()
//        }
//        gameThread.stopFlg = true
    }


}