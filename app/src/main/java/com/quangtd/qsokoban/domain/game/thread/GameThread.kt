package com.quangtd.qsokoban.domain.game.thread

import com.quangtd.qsokoban.common.CommonConstants
import com.quangtd.qsokoban.domain.game.gamemanager.GameManager
import com.quangtd.qsokoban.domain.game.gameview.GamePanel


/**
 * Created by quang.td95@gmail.com
 * on 9/2/2018.
 */
class GameThread(private var gameManager: GameManager, private var gamePanel: GamePanel) : Thread() {
    private var targetTime = 1000 / CommonConstants.FPS
    @Volatile
    var stopFlg = false
    @Volatile
    var renderFlg = true
    private var startRenderTime = 0L
    private var elapsedTime = 0L
    private var waitingTime = 0L

    override fun run() {
        while (!stopFlg) {
            if (renderFlg) {
                startRenderTime = System.currentTimeMillis()
                gameManager.update()
                gamePanel.draw()
                elapsedTime = System.currentTimeMillis() - startRenderTime
                waitingTime = targetTime - elapsedTime
                if (waitingTime < 0) waitingTime = 5
                Thread.sleep(waitingTime)
            } else {
                Thread.sleep(targetTime)
            }
        }
    }
}