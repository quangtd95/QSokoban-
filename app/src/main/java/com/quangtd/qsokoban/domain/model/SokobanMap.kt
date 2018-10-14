package com.quangtd.qsokoban.domain.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
data class SokobanMap(
        @SerializedName("m")
        var mapData: Array<String>,
        @SerializedName("s")
        var solution: String
) {
    lateinit var player: Player
    lateinit var wallList: ArrayList<Wall>
    lateinit var boxList: ArrayList<Box>
    lateinit var destList: ArrayList<Destination>

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SokobanMap

        if (!Arrays.equals(mapData, other.mapData)) return false
        if (solution != other.solution) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(mapData)
        result = 31 * result + solution.hashCode()
        return result
    }
}