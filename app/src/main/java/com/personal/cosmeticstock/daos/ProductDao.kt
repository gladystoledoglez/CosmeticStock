package com.personal.cosmeticstock.daos

import androidx.room.*
import com.personal.cosmeticstock.entities.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: ProductEntity): Long

    @Update
    suspend fun update(product: ProductEntity): Int

    @Query("UPDATE product SET is_active =:isActive WHERE id =:id")
    suspend fun setActive(id: Int, isActive: Boolean): Int

    @Query("UPDATE product SET is_active =:isActive")
    suspend fun setAllActive(isActive: Boolean): Int

    @Delete
    suspend fun delete(product: ProductEntity): Int

    @Query("SELECT *, (sale-cost) as gain FROM product")
    suspend fun getAll(): Array<ProductEntity>

    @Query(
        "SELECT id, SUM(cost) as cost, SUM(sale) as sale, SUM((sale-cost)) as gain, " +
                "COUNT() as is_active FROM product WHERE is_active == 1"
    )
    suspend fun getTotals(): ProductEntity
}

