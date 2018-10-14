package com.quangtd.qsokoban.util

import android.util.Log

/**
 * Created by QuangTD
 * on 1/14/2018.
 */
class LogUtils {

    companion object {
        private var isDebugMode = true
        private var TAG = "TAGG"

        fun getMode(): Boolean {
            return isDebugMode
        }

        fun setMode(mode: Boolean) {
            isDebugMode = mode
        }


        fun d(tag: String, msg: String) {
            if (isDebugMode) {
                Log.d(tag, msg)
            }
        }

        fun d(msg: String) {
            d(TAG, msg)
        }

        fun d(tag: String, msg: String, tr: Throwable) {
            if (isDebugMode) {
                Log.d(tag, msg, tr)
            }

        }


        fun e(tag: String, msg: String) {
            if (isDebugMode) {
                Log.e(tag, msg)
            }

        }

        fun e(msg: String) {
            e(TAG, msg)
        }

        fun e(msg: Float) {
            e(TAG, msg.toString())
        }

        fun e(msg: Int) {
            e(TAG, msg.toString())
        }

        fun e(msg: Long) {
            e(TAG, msg.toString())
        }

        fun e(msg: Char) {
            e(TAG, msg.toString())
        }

        fun e(msg: Double) {
            e(TAG, msg.toString())
        }


        fun e(tag: String, msg: String, tr: Throwable) {
            if (isDebugMode) {
                Log.e(tag, msg, tr)
            }

        }

        fun i(tag: String, msg: String) {
            if (isDebugMode) {
                Log.i(tag, msg)
            }

        }

        fun i(msg: String) {
            i(TAG, msg)
        }

        fun i(tag: String, msg: String, tr: Throwable) {
            if (isDebugMode) {
                Log.i(tag, msg, tr)
            }

        }

        fun v(tag: String, msg: String) {
            if (isDebugMode) {
                Log.v(tag, msg)
            }

        }

        fun v(msg: String) {
            v(TAG, msg)
        }

        fun v(tag: String, msg: String, tr: Throwable) {
            if (isDebugMode) {
                Log.v(tag, msg, tr)
            }

        }

        fun w(tag: String, msg: String) {
            if (isDebugMode) {
                Log.w(tag, msg)
            }

        }

        fun w(tag: String, msg: String, tr: Throwable) {
            if (isDebugMode) {
                Log.w(tag, msg, tr)
            }

        }

        fun w(msg: String) {
            w(TAG, msg)
        }
    }

}