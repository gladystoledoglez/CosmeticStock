package com.personal.cosmeticstock.extensions

fun Int?.orValue(value: Int) = this ?: value

fun Int?.orZero() = orValue(0)

fun Int?.orNoneId() = orValue(-1)

fun Int?.isMoreThanZero() = orZero() > 0
