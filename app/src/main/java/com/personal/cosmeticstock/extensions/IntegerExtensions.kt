package com.personal.cosmeticstock.extensions

val Int.Companion.ZERO: Int get() = 0

val Int.Companion.DECIMAL_PLACES: Int get() = 2

fun Int?.orValue(value: Int) = this ?: value

fun Int?.orZero() = orValue(Int.ZERO)

fun Int?.orNoneId() = orValue(-1)

fun Int?.isMoreThanZero() = orZero() > Int.ZERO
