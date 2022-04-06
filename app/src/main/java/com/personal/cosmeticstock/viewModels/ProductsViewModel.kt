package com.personal.cosmeticstock.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.personal.cosmeticstock.bases.BaseViewModel
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel
import com.personal.cosmeticstock.repositories.ProductsRepository
import kotlinx.coroutines.launch

class ProductsViewModel(private val repository: ProductsRepository) : BaseViewModel() {

    private val _products = MutableLiveData<List<ProductModel>>()
    val products: LiveData<List<ProductModel>> = _products

    private val _totals = MutableLiveData<TotalModel>()
    val totals: LiveData<TotalModel> = _totals

    fun listProducts() {
        launch {
            _products.value = repository.getProducts()
        }
    }

    fun activeProduct(item: ProductModel) {
        launch {
            _products.value = repository.activeProduct(item)
        }
    }

    fun activeProductsList(isActive: Boolean) {
        launch {
            _products.value = repository.activeProductsList(isActive)
        }
    }

    fun deleteProduct(item: ProductModel) {
        launch {
            _products.value = repository.deleteProduct(item)
        }
    }

    fun getTotals() {
        launch {
            _totals.value = repository.getTotals()
        }
    }
}