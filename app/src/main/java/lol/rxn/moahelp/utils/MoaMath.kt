package lol.rxn.moahelp.utils

import kotlin.math.roundToInt

class MoaMath {
    companion object {
        fun moaToRadius(moa: Float = 1f, range: Int): Float {
            val radiusAt100: Float = 2.9088821f
            return (range / 100.0f) * radiusAt100 * moa
        }

        fun radiusToMoa(radius: Float, range: Int): Float {
            val moaPerCmAt100m = 1f / 2.9088821f
            return moaPerCmAt100m * (100f / range) * radius
        }

        fun roundToPrecision(value: Float, precision: Int): Float {
            return (value * precision).roundToInt() / precision.toFloat()
        }
    }
}