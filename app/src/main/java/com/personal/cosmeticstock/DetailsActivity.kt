package com.personal.cosmeticstock

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.personal.cosmeticstock.database.AppDatabase
import com.personal.cosmeticstock.databinding.ActivityDetailsBinding
import com.personal.cosmeticstock.extensions.orFalse
import com.personal.cosmeticstock.extensions.setCheckedText
import com.personal.cosmeticstock.extensions.toCurrencyMaskedStr
import com.personal.cosmeticstock.fragments.ProductsFragment.Companion.ITEM_NAME
import com.personal.cosmeticstock.models.ProductModel
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
            ProductsRepository(AppDatabase.initialize(this))
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
                item?.updateFrom(
                    tieName.text,
                    tieCost.text,
                    tieSale.text,
                    scActive.isChecked
                )
                if (item?.isNotEmpty().orFalse()) {
                    viewModel.saveProduct(item)
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