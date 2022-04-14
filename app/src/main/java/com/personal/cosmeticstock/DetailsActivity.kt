package com.personal.cosmeticstock

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.personal.cosmeticstock.database.AppDatabase
import com.personal.cosmeticstock.databinding.ActivityDetailsBinding
import com.personal.cosmeticstock.extensions.*
import com.personal.cosmeticstock.fragments.ProductsFragment.Companion.ITEM_NAME
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel
import com.personal.cosmeticstock.repositories.ProductsRepository
import com.personal.cosmeticstock.viewModels.ProductDetailsViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: ProductDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ProductDetailsViewModel(
            ProductsRepository(AppDatabase.initialize(this).productDao())
        )
        val item = intent.getParcelableExtra<ProductModel>(ITEM_NAME)
        initializeComponentsFrom(item)
    }

    private fun initializeComponentsFrom(item: ProductModel?) {
        with(binding) {
            tieName.setText(item?.name.orEmpty())
            item?.values?.apply {
                tieCost.setText(cost.toCurrencyMaskedStr())
                tieSale.setText(sale.toCurrencyMaskedStr())
            }

            scActive.apply {
                isChecked = item?.isActive.orFalse()
                setCheckedText(R.string.active, R.string.inactive)
                setOnClickListener { setCheckedText(R.string.active, R.string.inactive) }
            }

            btnSave.setOnClickListener {
                val updatedItem = item?.copy(
                    name = tieName.text.toString(),
                    values = TotalModel(
                        cost = tieCost.text?.toCurrencyBigDecimal().orZero(),
                        sale = tieSale.text?.toCurrencyBigDecimal().orZero(),
                    ),
                    isActive = scActive.isChecked
                )
                if (updatedItem?.isNotEmpty().orFalse()) {
                    viewModel.saveProduct(updatedItem)
                    finish()
                } else {
                    Toast.makeText(
                        this@DetailsActivity, R.string.empty_fields_warning, Toast.LENGTH_SHORT
                    ).show()
                }
            }
            btnCancel.setOnClickListener { finish() }
        }
    }
}