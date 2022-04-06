package com.personal.cosmeticstock.extensions

import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

val String.Companion.EMPTY: String get() = ""

val String.Companion.DOT: String get() = "."

val String.Companion.COMMA: String get() = ","

val String.Companion.STR_DECIMAL_SEPARATOR: String
    get() = DecimalFormatSymbols.getInstance().decimalSeparator.toString()

fun String.toRoundedBigDecimal() = this.trim().toBigDecimal().rounded()

fun String.isNotEmptyAndLengthEqualTo(value: Int) = (isNotEmpty() && length == value)

fun String.toCurrencyBigDecimal() = if (String.STR_DECIMAL_SEPARATOR == String.COMMA) {
    this.replace(String.DOT, String.EMPTY).replace(String.COMMA, String.DOT)
} else {
    this.replace(String.COMMA, String.EMPTY)
}.toRoundedBigDecimal()

fun String.toCurrencyMaskedStr(): String {
    val isDecimals = true
    val currency = String.EMPTY
    val unmaskedText = this
        .replace("[$,.]".toRegex(), String.EMPTY)
        .replace("\\s+".toRegex(), String.EMPTY)
    val result = if (unmaskedText.isNotEmpty()) {
        val format = try {
            if (isDecimals) {
                val number: Double = (unmaskedText.toDouble() / 100)
                NumberFormat.getCurrencyInstance().let {
                    it.format(number).replace(it.currency?.symbol.orEmpty(), currency)
                }
            } else {
                val locale = Locale.getDefault()
                val parsed = unmaskedText.toInt()
                "$currency${NumberFormat.getNumberInstance(locale).format(parsed.toLong())}"
            }
        } catch (e: NumberFormatException) {
            null
        }
        if (String.STR_DECIMAL_SEPARATOR != String.COMMA && !isDecimals)
            format?.replace(String.COMMA.toRegex(), String.STR_DECIMAL_SEPARATOR)
        else
            format
    } else this
    return result ?: this
}