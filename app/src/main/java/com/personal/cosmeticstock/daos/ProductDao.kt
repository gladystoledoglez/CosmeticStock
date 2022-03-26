package com.personal.cosmeticstock.daos

import androidx.room.*
import com.personal.cosmeticstock.entities.ProductEntity

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(product: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("SELECT *, (sale-cost) as gain FROM product")
    suspend fun getAll(): Array<ProductEntity>

    @Query(
        "SELECT id, SUM(cost) as cost, SUM(sale) as sale, SUM((sale-cost)) as gain, " +
                "COUNT() as is_active FROM product WHERE is_active == 1"
    )
    suspend fun getTotals(): ProductEntity

}

