package com.personal.cosmeticstock.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.personal.cosmeticstock.bases.BaseViewModel
import com.personal.cosmeticstock.extensions.orFalse
import com.personal.cosmeticstock.mappers.toProductsListModel
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.ProductsListModel
import com.personal.cosmeticstock.models.TotalModel
import com.personal.cosmeticstock.repositories.ProductsRepository
import kotlinx.coroutines.launch

class ProductsViewModel(private val repository: ProductsRepository) : BaseViewModel() {

    private val _products = MutableLiveData<ProductsListModel>()
    val products: LiveData<ProductsListModel> = _products

    private val _totals = MutableLiveData<TotalModel>()
    val totals: LiveData<TotalModel> = _totals

    private fun getProductsList() = products.value?.products

    fun listProducts() {
        launch {
            _products.value = repository.getProducts().toProductsListModel()
        }
    }

    fun activeProduct(item: ProductModel) {
        launch {
            val list = getProductsList()?.toMutableList()
            val current = list?.find { it.id == item.id }
            current?.let {
                it.isActive = !item.isActive.orFalse()
                repository.updateProduct(it)
                _products.value = ProductsListModel(list.orEmpty(), list.indexOf(it))
            }
        }
    }

    fun activeProductsList(isActive: Boolean) {
        launch {
            val list = getProductsList()?.toMutableList()
            list?.forEach { it.isActive = isActive }
            repository.updateProductsList(list)
            _products.value = list?.toProductsListModel()
        }
    }

    fun deleteProduct(item: ProductModel) {
        launch {
            repository.deleteProduct(item)
            val list = getProductsList()?.minus(item)
            _products.value = list?.toProductsListModel()
        }
    }

    fun getTotals() {
        launch {
            _totals.value = repository.getTotals()
        }
    }
}