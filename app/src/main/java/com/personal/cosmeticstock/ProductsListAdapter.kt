package com.personal.cosmeticstock

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import com.personal.cosmeticstock.databinding.ItemProductBinding
import com.personal.cosmeticstock.models.ProductModel

class ProductsListAdapter(
    private val onItemEdit: (item: ProductModel) -> Unit,
    private val onItemActive: (item: ProductModel) -> Unit,
    private val onItemDelete: (item: ProductModel) -> Unit
) : ListAdapter<ProductModel, ProductsViewHolder>(ProductModel.DIFF_ITEM_CALLBACK), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsViewHolder(view, onItemEdit, onItemActive, onItemDelete)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = currentList[position]
        holder.bind(item)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(filterText: CharSequence?): FilterResults {
                val results = FilterResults()
                results.values = currentList.filter { it.containsInput(filterText.toString()) }
                return results
            }

            override fun publishResults(filterText: CharSequence?, filterResults: FilterResults?) {
                submitList(filterResults?.values as? List<ProductModel>?)
            }
        }
    }


}
