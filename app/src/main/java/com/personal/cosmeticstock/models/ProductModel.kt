package com.personal.cosmeticstock.models

import android.os.Parcelable
import android.text.Editable
import androidx.recyclerview.widget.DiffUtil
import com.personal.cosmeticstock.extensions.isMoreThanZero
import com.personal.cosmeticstock.extensions.orFalse
import com.personal.cosmeticstock.extensions.orZero
import com.personal.cosmeticstock.extensions.toCurrencyBigDecimal
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    var id: Int? = null,
    var name: String? = null,
    var values: TotalModel = TotalModel(),
    var isActive: Boolean? = false
) : Parcelable {

    fun updateFrom(
        editableName: Editable?,
        editableCost: Editable?,
        editableSale: Editable?,
        isChecked: Boolean?
    ) {
        name = editableName.toString()
        values = TotalModel(
            cost = editableCost?.takeIf {
                it.isNotEmpty()
            }?.toString()?.toCurrencyBigDecimal().orZero(),
            sale = editableSale?.takeIf {
                it.isNotEmpty()
            }?.toString()?.toCurrencyBigDecimal().orZero(),
        )
        isActive = isChecked
    }

    fun isNotEmpty() = name?.isNotEmpty().orFalse() &&
            values.cost.isMoreThanZero() &&
            values.sale.isMoreThanZero()

    fun containsInput(filterText: String): Boolean {
        return name?.contains(filterText) == true ||
                values.cost.toString().contains(filterText) ||
                values.sale.toString().contains(filterText) ||
                values.gain.toString().contains(filterText)
    }

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<ProductModel>() {
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.values.cost.orZero() == newItem.values.cost.orZero() &&
                        oldItem.values.sale.orZero() == newItem.values.sale.orZero() &&
                        oldItem.values.gain.orZero() == newItem.values.gain.orZero() &&
                        oldItem.isActive == newItem.isActive
            }
        }
    }
}
