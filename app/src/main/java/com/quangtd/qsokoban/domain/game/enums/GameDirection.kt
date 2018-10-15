package com.quangtd.qsokoban.domain.game.enums

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by quang.td95@gmail.com
 * on 9/2/2018.
 */
enum class GameDirection(var id: Int) {
    @SerializedName("up")
    UP(0),
    @SerializedName("right")
    RIGHT(1),
    @SerializedName("down")
    DOWN(2),
    @SerializedName("left")
    LEFT(3),
    @SerializedName("stop")
    STOP(5);
}