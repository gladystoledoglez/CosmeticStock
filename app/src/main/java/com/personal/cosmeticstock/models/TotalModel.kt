package com.personal.cosmeticstock.models

import android.os.Parcelable
import com.personal.cosmeticstock.R
import com.personal.cosmeticstock.extensions.DEFAULT_VALUE
import com.personal.cosmeticstock.extensions.isMoreThanZero
import com.personal.cosmeticstock.extensions.orZero
import kotlinx.parcelize.Parcelize

@Parcelize
data class TotalModel(
    var cost: Double = Double.DEFAULT_VALUE,
    var sale: Double = Double.DEFAULT_VALUE,
    var gain: Double = Double.DEFAULT_VALUE,
    var activeCount: Int = 0,
) : Parcelable {
    fun isActiveCountEqualTo(itemsCount: Int): Boolean {
        val activeCount = activeCount.orZero()
        return activeCount.isMoreThanZero() && activeCount == itemsCount
    }

    fun getGainStringRes() = if (gain.isMoreThanZero()) R.string.gain else R.string.waste

    fun getGainColorRes(isEnabled: Boolean) = if (isEnabled)
        if (gain.isMoreThanZero()) R.color.gain else R.color.waste
    else R.color.disable_text
}
