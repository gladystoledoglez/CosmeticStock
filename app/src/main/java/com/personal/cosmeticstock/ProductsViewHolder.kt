package com.personal.cosmeticstock

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.personal.cosmeticstock.databinding.ItemProductBinding
import com.personal.cosmeticstock.extensions.orFalse
import com.personal.cosmeticstock.extensions.toCurrencyMaskedStr
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel

class ProductsViewHolder(
    private val binding: ItemProductBinding,
    private val onItemEdit: (item: ProductModel) -> Unit,
    private val onItemActive: (item: ProductModel) -> Unit,
    private val onItemDelete: (item: ProductModel) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProductModel) {
        with(binding) {
            tvProduct.text = item.name
            tvCostValue.text = item.values.cost.toCurrencyMaskedStr()
            tvSaleValue.text = item.values.sale.toCurrencyMaskedStr()
            scActive.isChecked = item.isActive.orFalse()

            itemView.setOnClickListener { onItemEdit(item) }
            scActive.setOnClickListener {
                onItemActive(item)
                setActiveItem(item.values)
            }
            ibDelete.setOnClickListener { onItemDelete(item) }
        }
        setActiveItem(item.values)
    }

    private fun setActiveItem(values: TotalModel) {
        with(binding) {

            val context = root.context
            val isEnabled = scActive.isChecked

            tvProduct.isEnabled = isEnabled
            tvCost.isEnabled = isEnabled
            tvCostValue.isEnabled = isEnabled
            tvSale.isEnabled = isEnabled
            tvSaleValue.isEnabled = isEnabled
            tvBalance.isEnabled = isEnabled
            tvBalance.text = context.getString(values.getBalanceStringRes())
            tvBalanceValue.isEnabled = isEnabled
            tvBalanceValue.text = values.balance.toCurrencyMaskedStr()

            val color = ContextCompat.getColor(context, values.getBalanceColorRes(isEnabled))
            tvBalance.setTextColor(color)
            tvBalanceValue.setTextColor(color)
        }
    }
}
