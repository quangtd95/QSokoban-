package com.quangtd.qsokoban.domain.game.enums

import com.google.gson.annotations.SerializedName

/**
 * Created by quang.td95@gmail.com
 * on 9/3/2018.
 */
enum class GameState {
    @SerializedName("loading")
    LOADING,
    @SerializedName("loaded")
    LOADED,
    @SerializedName("intro")
    INTRO,
    @SerializedName("playing")
    PLAYING,
    @SerializedName("pause")
    PAUSE,
    @SerializedName("stop")
    STOP,
    @SerializedName("win_game")
    WIN_GAME,
    @SerializedName("lose_game")
    LOSE_GAME;

    interface GameStateCallBack {
        fun onGameStateChangeCallback(gameState: GameState)
    }
}