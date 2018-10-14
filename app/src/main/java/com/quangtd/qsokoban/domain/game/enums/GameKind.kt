package com.quangtd.qsokoban.domain.game.enums

/**
 * Created by quang.td95@gmail.com
 * on 10/14/2018.
 */
enum class GameKind(var value: Int) {
    CLASSIC(0);

    companion object {
        fun getByValue(value: Int): GameKind {
            values().forEach {
                if (it.value == value) {
                    return it
                }
            }
            throw IllegalArgumentException()
        }
    }

}