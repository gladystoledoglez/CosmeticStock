package com.personal.cosmeticstock.extensions

import android.text.Editable

fun Editable.takeIfIsNotEmpty() = takeIf { it.isNotEmpty() }

fun Editable.toCurrencyBigDecimal() = takeIfIsNotEmpty()?.toString()?.toCurrencyBigDecimal()