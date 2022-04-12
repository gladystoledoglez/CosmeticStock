package com.personal.cosmeticstock.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.personal.cosmeticstock.database.AppDatabase.Companion.PRODUCT_TABLE_NAME

@Entity(tableName = PRODUCT_TABLE_NAME)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = NAME) val name: String?,
    @ColumnInfo(name = COST) val cost: Double?,
    @ColumnInfo(name = SALE) val sale: Double?,
    @ColumnInfo(name = GAIN) val gain: Double?,
    @ColumnInfo(name = IS_ACTIVE) val active: Int?
) {
    companion object {
        const val NAME = "name"
        const val COST = "cost"
        const val SALE = "sale"
        const val GAIN = "gain"
        const val IS_ACTIVE = "is_active"
    }
}

