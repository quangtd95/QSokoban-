package com.quangtd.qsokoban.ui.screen.game

import com.quangtd.qsokoban.common.CommonConstants
import com.quangtd.qsokoban.domain.game.enums.GameDirection
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
    private lateinit var _level: Level
    private var highScore = 0

    override fun onInit() {
        gameDataRepository = GameDataRepositoryImpl()
    }

    fun setUpGame(level: Level) {
        _level = gameDataRepository.loadGameData(getContext()!!, level.id)
        highScore = _level.savedMove
        gameManager = GameManager(_level)
        gameManager.loadGame(getContext()!!)
        gamePanel = GamePanel(getContext()!!, gameManager, getIView()!!.getSurfaceHolder())
        gameManager.bindGameStateCallback(this)
        gameManager.bindRenderCallback(this)
        gamePanel.bindRenderCalBack(this)
        gamePanel.loadGameUI()
        gameThread = GameThread(gameManager, gamePanel)
        gameThread.start()
        getIView()?.setHeightGame(gamePanel.getHeight())
        getIView()?.setBoomNumber(gameDataRepository.loadBoomNumber(getContext()!!))
        getIView()?.setCoinNumber(gameDataRepository.loadCoin(getContext()!!))

    }

    fun move(direction: OnSwipeListener.Direction) {
        if (!isPlaying()) return
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
            if (gameManager.getMoveStep() > gameManager.getTarget()) {
                _level.ranking = 2
            }
            if (gameManager.getMoveStep() > (gameManager.getTarget() + 10) && _level.ranking >= 2) {
                _level.ranking = 1
            }
            if (gameManager.getMoveStep() <= gameManager.getTarget()) {
                _level.ranking = 3
            }
            _level.savedMove = gameManager.getMoveStep()
            getIView()?.setMoved(gameManager.getMoveStep(), _level.ranking)
        }
    }

    private fun isPlaying(): Boolean {
        if (gameThread.stopFlg) return false
        return gameThread.renderFlg
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
        when (gameState) {
            GameState.WIN_GAME -> {
                if (_level.isComplete) {
                    if (highScore != 0 && highScore > _level.savedMove) {
                        getIView()?.showWinGameBonusCoinAlert(getCoinBonus())
                    } else {
                        getIView()?.showWinGameAlert()
                    }
                } else {
                    getIView()?.showWinGameBonusCoinAlert(getCoinBonus())
                }
                updateWinData()
                pauseGame()
            }
            GameState.LOSE_GAME -> {
                getIView()?.showLoseGameAlert()
            }

            else -> {//do nothing
            }
        }
    }

    private fun getCoinBonus(): Int {
        var coinBonus = 0
        if (_level.ranking == 3) {
            coinBonus = CommonConstants.REWARD_COIN_3
        }
        if (_level.ranking == 2) {
            coinBonus = CommonConstants.REWARD_COIN_2
        }
        if (_level.ranking == 1) {
            coinBonus = CommonConstants.REWARD_COIN_1
        }
        return coinBonus
    }

    private fun updateWinData() {
        var coinBonus = 0
        if (_level.savedMove > highScore && _level.isComplete && highScore != 0) {
            _level.savedMove = highScore
        } else {
            highScore = _level.savedMove
            coinBonus = getCoinBonus()
        }
        if (!_level.isComplete) {
            coinBonus = getCoinBonus()
        }
        gameDataRepository.saveCoin(getContext()!!, gameDataRepository.loadCoin(getContext()!!) + coinBonus)
        if (gameDataRepository.loadCoin(getContext()!!) >= CommonConstants.BOOM_PRICE) {
            gameDataRepository.saveBoomNumber(getContext()!!, gameDataRepository.loadBoomNumber(getContext()!!) + 1)
            gameDataRepository.saveCoin(getContext()!!, gameDataRepository.loadCoin(getContext()!!) - CommonConstants.BOOM_PRICE)
            getIView()?.setBoomNumber(gameDataRepository.loadBoomNumber(getContext()!!))
            getIView()?.showRewardBoom()
        }

        getIView()?.setCoinNumber(gameDataRepository.loadCoin(getContext()!!))


        _level.isComplete = true
        gameDataRepository.saveGameData(getContext()!!, _level)
        val nextLevel: Level = if (_level.id < 1000) {
            gameDataRepository.loadGameData(getContext()!!, _level.id + 1)
        } else {
            gameDataRepository.loadGameData(getContext()!!, 1)
        }
        nextLevel.isUnlock = true
        gameDataRepository.saveGameData(getContext()!!, nextLevel)
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

    fun userItem(b: Boolean) {
        gameThread.chooseBoomFlg = b
        gamePanel.userBoom(b)
    }


    fun reloadGame() {
        pauseGame()
        getIView()?.setMoved(0, 3)
        getIView()?.setBest(highScore)
        gameManager.reloadGame()
        resumeGame()
    }

    fun moveNextLevel() {
        val nextLevel: Level = if (_level.id < 1000) {
            gameDataRepository.loadGameData(getContext()!!, _level.id + 1)
        } else {
            gameDataRepository.loadGameData(getContext()!!, 1)
        }
        getIView()?.moveNextLevel(nextLevel)

    }

    fun useBoom() {
        if (gameDataRepository.loadBoomNumber(getContext()!!) > 0) {
            pauseGame()
            userItem(true)
            getIView()?.chooseWallToDestroy(true)
        } else {
            getIView()?.notEnoughBoomAlert()
        }
    }

    fun cancelUseBoom() {
        resumeGame()
        userItem(false)
        getIView()?.chooseWallToDestroy(false)
        gameManager.setBoomPosition(null)
    }

    fun setBoomPosition(x: Float, y: Float) {
        val point = gamePanel.convertPxToPoint(x, y)
        gameManager.setBoomPosition(point)
    }

    fun destroyWall() {
        if (gameDataRepository.loadBoomNumber(getContext()!!) > 0) {
            if (gameManager.checkHasPlaceBoom()) {
                gameDataRepository.saveBoomNumber(getContext()!!, gameDataRepository.loadBoomNumber(getContext()!!) - 1)
                gameManager.destroyWall {
                    cancelUseBoom()
                }
                getIView()?.setBoomNumber(gameDataRepository.loadBoomNumber(getContext()!!))
            } else {
                getIView()?.placeBoomToDestroyAlert()
            }
        } else {
            getIView()?.notEnoughBoomAlert()
        }

    }

    fun isWin(): Boolean = gameManager.isWin()

}