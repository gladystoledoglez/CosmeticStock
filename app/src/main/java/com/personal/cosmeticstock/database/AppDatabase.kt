package com.personal.cosmeticstock.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.personal.cosmeticstock.daos.ProductDao
import com.personal.cosmeticstock.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private const val DB_NAME = "cosmetics_stock_db"
        const val PRODUCT_TABLE_NAME = "product_table"
        fun initialize(context: Context) = Room.databaseBuilder(
            context, AppDatabase::class.java, DB_NAME
        ).build()
    }
}