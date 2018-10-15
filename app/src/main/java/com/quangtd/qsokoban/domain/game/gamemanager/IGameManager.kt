package com.quangtd.qsokoban.domain.game.gamemanager

import com.quangtd.qsokoban.domain.game.enums.GameDirection
import com.quangtd.qsokoban.domain.model.SokobanMap

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
interface IGameManager {
    fun getSokobanMap(): SokobanMap
    fun update()
    fun action(direction: GameDirection)
}