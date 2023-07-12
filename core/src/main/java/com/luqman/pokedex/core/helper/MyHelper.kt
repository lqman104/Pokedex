package com.luqman.pokedex.core.helper

import java.math.RoundingMode
import java.text.DecimalFormat

object MyHelper {
    fun Double.roundOffDecimal(): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(this).toDouble()
    }
}