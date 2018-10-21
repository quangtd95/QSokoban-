package com.quangtd.qsokoban.domain.game.gamemanager

import android.graphics.Point
import com.quangtd.qsokoban.domain.game.enums.GameDirection
import com.quangtd.qsokoban.domain.game.enums.GameState
import com.quangtd.qsokoban.domain.game.enums.RenderState
import com.quangtd.qsokoban.domain.model.SokobanMap

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
interface IGameManager {
    fun getSokobanMap(): SokobanMap
    fun update()
    fun action(direction: GameDirection): Boolean
    fun bindGameStateCallback(gameStateCallback: GameState.GameStateCallBack)
    fun bindRenderCallback(renderCallBack: RenderState.RenderCallback)
    fun increaseMoveStepCount()
    fun getMoveStep(): Int
    fun getTarget(): Int
    fun reloadGame()
    fun setBoomPosition(point: Point?)
    fun destroyWall(l: (Unit) -> Unit)
    fun checkHasPlaceBoom(): Boolean
}