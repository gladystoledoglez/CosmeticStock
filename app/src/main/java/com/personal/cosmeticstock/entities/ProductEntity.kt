package com.personal.cosmeticstock.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "cost") val cost: Double?,
    @ColumnInfo(name = "sale") val sale: Double?,
    @ColumnInfo(name = "gain") val gain: Double?,
    @ColumnInfo(name = "is_active") val active: Int?
)