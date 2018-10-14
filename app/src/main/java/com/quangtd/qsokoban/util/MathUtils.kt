package com.quangtd.qsokoban.util

/**
 * Created by QuangTD
 * on 1/31/2018.
 */
class MathUtils {
    companion object {
        /**
         * return in 0.0..1
         */
        fun toPercentWithRange(start: Float, end: Float, current: Float): Float {
            return ((current - start) / (end - start))
        }

        fun toPercentWithRange(start: Int, end: Int, current: Int): Float {
            return ((current - start).toFloat() / (end - start))
        }

        fun toPercentWithTotal(start: Int, total: Int, current: Int): Float {
            return ((current - start).toFloat()) / (total)
        }

        fun toPercentWithTotal(start: Float, total: Float, current: Float): Float {
            return (current - start) / total
        }

        fun clamp(value: Float, min: Float, max: Float): Float {
            if (value < min) {
                return min
            } else if (value > max) {
                return max
            }
            return value
        }
        fun clamp(value: Double, min: Double, max: Double): Double {
            if (value < min) {
                return min
            } else if (value > max) {
                return max
            }
            return value
        }

        fun clamp(value: Int, min: Int, max: Int): Int {
            if (value < min) {
                return min
            } else if (value > max) {
                return max
            }
            return value
        }

    }
}