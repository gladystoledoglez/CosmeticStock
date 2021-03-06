package com.personal.cosmeticstock.repositories

import com.personal.cosmeticstock.daos.ProductDao
import com.personal.cosmeticstock.extensions.orFalse
import com.personal.cosmeticstock.extensions.orZero
import com.personal.cosmeticstock.mappers.toEntity
import com.personal.cosmeticstock.mappers.toListModel
import com.personal.cosmeticstock.mappers.toTotalModel
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel

class ProductsRepository(private val productDao: ProductDao) {

    private suspend fun getProductsBy(result: Long) = if (result >= 1)
        getProducts()
    else
        emptyList()

    suspend fun getProducts(): List<ProductModel> {
        return productDao.getAll().toList().toListModel()
    }

    suspend fun saveProduct(product: ProductModel): List<ProductModel> {
        val result = if (product.id.orZero() >= 1)
            productDao.update(product.toEntity())
        else
            productDao.insert(product.toEntity())
        return getProductsBy(result.toLong())
    }

    suspend fun activeProduct(product: ProductModel): List<ProductModel> {
        val result = productDao.setActive(
            product.id.orZero(), !product.isActive.orFalse()
        )
        return getProductsBy(result.toLong())
    }

    suspend fun activeProductsList(setAllActive: Boolean): List<ProductModel> {
        val result = productDao.setAllActive(setAllActive)
        return getProductsBy(result.toLong())
    }

    suspend fun deleteProduct(product: ProductModel): List<ProductModel> {
        val result = productDao.delete(product.toEntity())
        return getProductsBy(result.toLong())
    }

    suspend fun getTotals(): TotalModel {
        return productDao.getTotals().toTotalModel()
    }

}