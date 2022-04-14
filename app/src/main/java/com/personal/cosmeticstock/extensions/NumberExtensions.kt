package com.personal.cosmeticstock.extensions

import java.math.BigDecimal
import java.math.RoundingMode


/**
 * BigDecimal related functions.
 * **/
fun BigDecimal.rounded(): BigDecimal = setScale(Int.DECIMAL_PLACES, RoundingMode.HALF_UP)

fun BigDecimal?.orValue(value: BigDecimal) = this ?: value

fun BigDecimal?.orZero(): BigDecimal = orValue(BigDecimal.ZERO)

fun BigDecimal?.isMoreThanZero() = orZero() > BigDecimal.ZERO

fun BigDecimal?.isZero() = orZero() == BigDecimal.ZERO.rounded()

fun BigDecimal?.toCurrencyMaskedStr() = toString().toCurrencyMaskedStr()

/**
 * Double related variables and functions.
 * **/
val Double.Companion.DEFAULT_VALUE: Double get() = 0.0

fun Double?.orZero() = this ?: Double.DEFAULT_VALUE

/**
 * Integer related variables and functions.
 * **/
val Int.Companion.ZERO: Int get() = 0

val Int.Companion.DECIMAL_PLACES: Int get() = 2

fun Int?.orValue(value: Int) = this ?: value

fun Int?.orZero() = orValue(Int.ZERO)

fun Int?.isMoreThanZero() = orZero() > Int.ZERO