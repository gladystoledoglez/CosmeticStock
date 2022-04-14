package com.personal.cosmeticstock.models

import android.os.Parcelable
import com.personal.cosmeticstock.R
import com.personal.cosmeticstock.extensions.isMoreThanZero
import com.personal.cosmeticstock.extensions.isZero
import com.personal.cosmeticstock.extensions.orZero
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class TotalModel(
    val cost: BigDecimal = BigDecimal.ZERO,
    val sale: BigDecimal = BigDecimal.ZERO,
    val balance: BigDecimal = BigDecimal.ZERO,
    val activeCount: Int = 0,
) : Parcelable {
    fun isActiveCountEqualTo(itemsCount: Int): Boolean {
        return isAnyActive() && activeCount.orZero() == itemsCount
    }

    private fun isAnyActive(): Boolean = activeCount.isMoreThanZero()

    private fun isZeroWasted() = (getBalanceStringRes() == R.string.waste && balance.isZero())

    fun getBalanceStringRes() = if (balance.isMoreThanZero()) R.string.gain else R.string.waste

    fun getBalanceColorRes(isEnabled: Boolean = isAnyActive()) = if (isEnabled)
        if (balance.isMoreThanZero()) R.color.gain else R.color.waste
    else R.color.disable_text

    fun isBalanceVisible() = isAnyActive() && !isZeroWasted()
}
