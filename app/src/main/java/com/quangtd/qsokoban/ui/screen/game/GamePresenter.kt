package com.quangtd.qsokoban.ui.screen.game

import com.quangtd.qsokoban.domain.game.enums.GameDirection
import com.quangtd.qsokoban.domain.game.enums.GameKind
import com.quangtd.qsokoban.domain.game.enums.GameState
import com.quangtd.qsokoban.domain.game.enums.RenderState
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
class GamePresenter : BasePresenter<GameView>(), GameState.GameStateCallBack, RenderState.RenderCallback {

    private lateinit var gameManager: GameManager
    private lateinit var gamePanel: GamePanel
    private lateinit var gameThread: GameThread

    private lateinit var gameDataRepository: GameDataRepository

    override fun onInit() {
        gameDataRepository = GameDataRepositoryImpl()
    }

    fun setUpGame(level: Level) {
        var _level = gameDataRepository.loadData(getContext()!!, level.id)
        gameManager = GameManager(_level)
        gameManager.loadGame(getContext()!!)
        gamePanel = GamePanel(getContext()!!, gameManager, getIView()!!.getSurfaceHolder())
        gameManager.bindGameStateCallback(this)
        gameManager.bindRenderCallback(this)
        gamePanel.bindRenderCalBack(this)
        gamePanel.loadGameUI()
        gameThread = GameThread(gameManager, gamePanel)
        gameThread.start()

    }

    fun move(direction: OnSwipeListener.Direction) {
        resumeGame()
        val canMove = when (direction) {
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
        if (canMove) {
            gameManager.increaseMoveStepCount()
            getIView()?.setMoved(gameManager.getMoveStep())
        }
    }

    fun resumeGame() {
        gameThread.renderFlg = true
    }

    fun pauseGame() {
        gameThread.renderFlg = false
    }

    fun stopGame() {
        gameThread.stopFlg = true
    }

    override fun onGameStateChangeCallback(gameState: GameState) {
        LogUtils.e(gameState.name)
//        gamePanel.setState(gameState)
        when (gameState) {
            GameState.LOADING -> {
            }
            GameState.LOADED -> {
                gameManager.forceChangeGameState(GameState.INTRO)
            }
            GameState.INTRO -> {
//                gamePanel.resetValue()
            }
            GameState.PLAYING -> {
                /*if (level.gameKind == GameKind.TIME_TRIAL) {
                    (gameManager!! as TimeTrialGameManager).playBackgroundSound()
                }*/
//                gameManager!!.resetStartGameTime()
            }
            GameState.PAUSE -> {
            }
            GameState.STOP -> {
            }
            GameState.WIN_GAME -> {
                getIView()?.showWinGameAlert()
//                updateData()
                stopGame()
            }
            GameState.LOSE_GAME -> {
                getIView()?.showLoseGameAlert()
            }
        }
    }

    override fun changeRenderState(renderState: RenderState) {
        when (renderState) {
            RenderState.REQUEST_RENDER -> {
                resumeGame()
            }
            RenderState.STOP_RENDER -> {
                pauseGame()
            }
        }
    }

    fun getMoveStep(): Int {
        return gameManager.getMoveStep()
    }

    fun getTarget(): Int {
        return gameManager.getTarget()
    }

}