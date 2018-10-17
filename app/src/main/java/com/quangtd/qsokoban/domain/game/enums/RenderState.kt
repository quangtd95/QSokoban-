package com.quangtd.qsokoban.domain.game.enums

import com.google.gson.annotations.SerializedName

/**
 * Created by quang.td95@gmail.com
 * on 9/3/2018.
 */
enum class RenderState {
    @SerializedName("request_render")
    REQUEST_RENDER,
    @SerializedName("stop_render")
    STOP_RENDER;

    interface RenderCallback {
        fun changeRenderState(renderState: RenderState)
    }
}