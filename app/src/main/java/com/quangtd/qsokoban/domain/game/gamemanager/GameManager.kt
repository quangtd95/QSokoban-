package com.quangtd.qsokoban.domain.game.gamemanager

import android.content.Context
import com.google.gson.Gson
import com.quangtd.qsokoban.common.CommonConstants.Companion.MAP_NAME_TEMPLATE
import com.quangtd.qsokoban.domain.game.enums.GameDirection
import com.quangtd.qsokoban.domain.game.enums.GameState
import com.quangtd.qsokoban.domain.model.*
import com.quangtd.qsokoban.util.ScreenUtils

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
class GameManager(private var level: Level) : IGameManager {

    private lateinit var gameState: GameState
    private lateinit var map: SokobanMap
    private lateinit var player: Player
    private var wallList = ArrayList<Wall>()
    private var boxList = ArrayList<Box>()
    private var destList = ArrayList<Destination>()

    private var rows = 0
    private var cols = 0
    private var targetStep = 0

    private var widthCell = 0F
    private var startTimestamp = 0L
    private var gameStateCallback: GameState.GameStateCallBack? = null

    fun loadGame(context: Context) {
        forceChangeGameState(GameState.LOADING)
        //load image
//        BitmapManager.initResource(context)
        //load sound
//        soundManager = SoundManager.getInstance()
        //load map
        loadMap(context, level)
        initGame(context)
        forceChangeGameState(GameState.LOADED)
    }

    private fun loadMap(context: Context, level: Level) {
        val inputStream = context.assets.open(String.format(MAP_NAME_TEMPLATE, level.id))
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        map = Gson().fromJson(String(buffer), SokobanMap::class.java)
    }

    private fun initGame(context: Context) {
        rows = map.mapData.size
        map.mapData.forEach { rowData ->
            if (cols < rowData.length) {
                cols = rowData.length

            }
        }
        targetStep = map.solution.length
        widthCell = ScreenUtils.getWidthScreen(context).toFloat() / ((if (rows > cols) rows else cols) + 2)

        map.mapData.forEachIndexed { row, rowData ->
            if (cols < rowData.length) {
                cols = rowData.length
            }
            rowData.forEachIndexed { col, colData ->
                when (colData) {
                    'W' -> {
                        val wall = Wall(row, col)
                        wall.widthCell = widthCell
                        wallList.add(wall)
                    }
                    'P' -> {
                        player = Player(row, col, map)
                        player.widthCell = widthCell
                    }

                    'B' -> {
                        val box = Box(row, col)
                        box.map = map
                        box.widthCell = widthCell
                        boxList.add(box)
                    }
                    'X' -> {
                        val destination = Destination(row, col)
                        destination.widthCell = widthCell
                        destList.add(destination)
                    }
                    else -> {
                        // do-nothing
                    }
                }
            }
        }
        map.boxList = boxList
        map.destList = destList
        map.wallList = wallList
        map.player = player
    }

    fun forceChangeGameState(gameState: GameState) {
        this.gameState = gameState
        gameStateCallback?.onGameStateChangeCallback(gameState)
    }

    override fun getSokobanMap(): SokobanMap {
        return map
    }

    override fun update() {
        player.update()
    }

    override fun action(direction: GameDirection) {
        player.move(direction)
    }
}