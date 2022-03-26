package com.personal.cosmeticstock.repositories

import com.personal.cosmeticstock.database.ProductDatabase
import com.personal.cosmeticstock.extensions.orZero
import com.personal.cosmeticstock.mappers.toEntity
import com.personal.cosmeticstock.mappers.toListModel
import com.personal.cosmeticstock.mappers.toTotalModel
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel

class ProductsRepository(private val database: ProductDatabase) {

    suspend fun getProducts(): List<ProductModel> {
        return database.productDao().getAll().toList().toListModel()
    }

    suspend fun saveProduct(product: ProductModel) {
        if (product.id.orZero() >= 1)
            database.productDao().update(product.toEntity())
        else
            database.productDao().insert(product.toEntity())
    }

    suspend fun updateProduct(product: ProductModel) {
        database.productDao().update(product.toEntity())
    }

    suspend fun updateProductsList(products: List<ProductModel>?) {
        products?.forEach { product -> updateProduct(product) }
    }

    suspend fun deleteProduct(product: ProductModel) {
        database.productDao().delete(product.toEntity())
    }

    suspend fun getTotals(): TotalModel {
        return database.productDao().getTotals().toTotalModel()
    }

}