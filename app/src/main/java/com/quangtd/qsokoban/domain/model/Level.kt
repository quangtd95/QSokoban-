package com.quangtd.qsokoban.domain.model

import com.google.gson.annotations.SerializedName
import com.quangtd.qsokoban.domain.game.enums.GameKind
import com.quangtd.qsokoban.mvpbase.BaseModel
import java.io.Serializable

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
data class Level(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("gameKind")
        var gameKind: Int = GameKind.CLASSIC.value,
        @SerializedName("isComplete")
        var isComplete: Boolean = false,
        @SerializedName("isUnlock")
        var isUnlock: Boolean = false,
        @SerializedName("savedMove")
        var savedMove: Int = 0,
        @SerializedName("ranking")
        var ranking: Int = 0) : BaseModel, Serializable