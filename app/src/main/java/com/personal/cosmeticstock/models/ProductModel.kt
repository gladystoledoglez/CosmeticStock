package com.personal.cosmeticstock.models

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.personal.cosmeticstock.extensions.isMoreThanZero
import com.personal.cosmeticstock.extensions.orFalse
import com.personal.cosmeticstock.extensions.orZero
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val id: Int? = null,
    val name: String? = null,
    val values: TotalModel = TotalModel(),
    val isActive: Boolean? = false
) : Parcelable {

    fun isNotEmpty() = name?.isNotEmpty().orFalse() &&
            values.cost.isMoreThanZero() &&
            values.sale.isMoreThanZero()

    fun containsInput(filterText: String): Boolean {
        return name?.contains(filterText) == true ||
                values.cost.toString().contains(filterText) ||
                values.sale.toString().contains(filterText) ||
                values.balance.toString().contains(filterText)
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
                        oldItem.values.balance.orZero() == newItem.values.balance.orZero() &&
                        oldItem.isActive == newItem.isActive
            }
        }
    }
}
