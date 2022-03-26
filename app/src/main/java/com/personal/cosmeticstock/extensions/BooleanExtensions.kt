package com.personal.cosmeticstock.extensions

fun Boolean?.orFalse() = this ?: false

fun Boolean?.toInt() = if (this == true) 1 else 0