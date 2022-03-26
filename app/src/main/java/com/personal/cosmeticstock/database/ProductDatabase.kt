package com.personal.cosmeticstock.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.personal.cosmeticstock.daos.ProductDao
import com.personal.cosmeticstock.entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        private const val DAB_NAME = "product"
        fun initialize(context: Context) = Room.databaseBuilder(
            context, ProductDatabase::class.java, DAB_NAME
        ).build()
    }
}