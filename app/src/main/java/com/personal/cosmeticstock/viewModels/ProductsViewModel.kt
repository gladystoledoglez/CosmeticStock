package com.personal.cosmeticstock.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.personal.cosmeticstock.bases.BaseViewModel
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

    fun listProducts() {
        launch {
            _products.value = repository.getProducts().toProductsListModel()
        }
    }

    fun setListProducts(list: List<ProductModel>) {
        _products.value = list.toProductsListModel()
    }

    fun activeProduct(item: ProductModel) {
        launch {
            _products.value = repository.activeProduct(item).toProductsListModel()
        }
    }

    fun activeProductsList(isActive: Boolean) {
        launch {
            _products.value = repository.activeProductsList(isActive).toProductsListModel()
        }
    }

    fun deleteProduct(item: ProductModel) {
        launch {
            _products.value = repository.deleteProduct(item).toProductsListModel()
        }
    }

    fun getTotals() {
        launch {
            _totals.value = repository.getTotals()
        }
    }
}