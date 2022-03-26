package com.personal.cosmeticstock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.personal.cosmeticstock.database.ProductDatabase
import com.personal.cosmeticstock.databinding.ActivityMainBinding
import com.personal.cosmeticstock.extensions.transitionTo
import com.personal.cosmeticstock.fragments.ProductsFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var database: ProductDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = ProductDatabase.initialize(this)
        supportFragmentManager.transitionTo(ProductsFragment.newInstance(), isBackStack = false)
    }
}