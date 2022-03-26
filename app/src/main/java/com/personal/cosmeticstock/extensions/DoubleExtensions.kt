package com.personal.cosmeticstock.extensions

val Double.Companion.DEFAULT_VALUE: Double get() = 0.0

fun Double?.orZero() = this ?: Double.DEFAULT_VALUE

fun Double?.isMoreThanZero() = orZero() > 0