package com.personal.cosmeticstock.daos

import androidx.room.*
import com.personal.cosmeticstock.database.AppDatabase.Companion.PRODUCT_TABLE_NAME
import com.personal.cosmeticstock.entities.ProductEntity
import com.personal.cosmeticstock.entities.ProductEntity.Companion.COST
import com.personal.cosmeticstock.entities.ProductEntity.Companion.GAIN
import com.personal.cosmeticstock.entities.ProductEntity.Companion.IS_ACTIVE
import com.personal.cosmeticstock.entities.ProductEntity.Companion.SALE

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: ProductEntity): Long

    @Update
    suspend fun update(product: ProductEntity): Int

    @Query("UPDATE $PRODUCT_TABLE_NAME SET $IS_ACTIVE =:isActive WHERE id =:id")
    suspend fun setActive(id: Int, isActive: Boolean): Int

    @Query("UPDATE $PRODUCT_TABLE_NAME SET $IS_ACTIVE =:isActive")
    suspend fun setAllActive(isActive: Boolean): Int

    @Delete
    suspend fun delete(product: ProductEntity): Int

    @Query("SELECT *, ($SALE-$COST) as $GAIN FROM $PRODUCT_TABLE_NAME")
    suspend fun getAll(): Array<ProductEntity>

    @Query(
        "SELECT id, SUM($COST) as $COST, SUM($SALE) as $SALE, SUM($SALE-$COST) as $GAIN, " +
                "COUNT() as $IS_ACTIVE FROM $PRODUCT_TABLE_NAME WHERE $IS_ACTIVE == 1"
    )
    suspend fun getTotals(): ProductEntity
}

