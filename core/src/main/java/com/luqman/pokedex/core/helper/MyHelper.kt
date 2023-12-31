package com.luqman.pokedex.core.helper

import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Locale

object MyHelper {
    fun Double.roundOffDecimal(): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(this).toDouble()
    }


    fun String.titleCase(): String {
        return this.lowercase()
            .replaceFirstChar { it.titlecase(Locale.getDefault()) }
    }
}