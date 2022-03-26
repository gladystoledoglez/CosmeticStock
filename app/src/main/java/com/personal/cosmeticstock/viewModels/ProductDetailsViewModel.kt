package com.personal.cosmeticstock.viewModels

import com.personal.cosmeticstock.bases.BaseViewModel
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.repositories.ProductsRepository
import kotlinx.coroutines.launch

class ProductDetailsViewModel(private val repository: ProductsRepository) : BaseViewModel() {
    fun saveProduct(product: ProductModel?) {
        product?.let {
            launch { repository.saveProduct(it) }
        }
    }
}