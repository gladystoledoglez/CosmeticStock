package com.personal.cosmeticstock.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.personal.cosmeticstock.DetailsActivity
import com.personal.cosmeticstock.MainActivity
import com.personal.cosmeticstock.ProductsListAdapter
import com.personal.cosmeticstock.R
import com.personal.cosmeticstock.databinding.FragmentProductsBinding
import com.personal.cosmeticstock.extensions.orFalse
import com.personal.cosmeticstock.extensions.toCurrencyMaskedStr
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel
import com.personal.cosmeticstock.repositories.ProductsRepository
import com.personal.cosmeticstock.viewModels.ProductsViewModel

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel
    private lateinit var mainActivity: MainActivity

    private val adapter by lazy {
        ProductsListAdapter(
            ::onItemEdit, viewModel::activeProduct, viewModel::deleteProduct, ::showResultsMessage
        )
    }

    private var rvProducts: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProductsBinding.inflate(layoutInflater)
        mainActivity = (activity as MainActivity)
        viewModel = ProductsViewModel(
            ProductsRepository(mainActivity.database)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initListeners()
        mainActivity.binding.fabAdd.setOnClickListener { onItemEdit(ProductModel()) }

        val search = binding.lytHeader.svProduct
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(queryText: String?): Boolean {
                filterAdapterBy(queryText)
                return false
            }

            override fun onQueryTextChange(queryText: String?): Boolean {
                filterAdapterBy(queryText)
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        rvProducts?.adapter = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE)
            viewModel.listProducts()
    }

    private fun filterAdapterBy(text: String?) {
        if (text.isNullOrEmpty())
            viewModel.listProducts()
        else
            adapter.filter.filter(text)
    }

    private fun initObservers() {
        with(viewModel) {
            totals.observe(viewLifecycleOwner) { setupHeader(it) }
            products.observe(viewLifecycleOwner) { adapter.submitList(it.toMutableList()) { getTotals() } }
            listProducts()
        }
    }

    private fun initListeners() {
        with(binding) {
            lytHeader.scActive.apply {
                setOnClickListener { viewModel.activeProductsList(isChecked) }
            }
            rvProducts.adapter = adapter
        }
    }

    private fun setupHeader(summary: TotalModel) {
        val isChecked = summary.isActiveCountEqualTo(adapter.itemCount).orFalse()
        val gainColor = ContextCompat.getColor(requireContext(), summary.getGainColorRes(isChecked))
        val gainText = requireContext().getString(summary.getGainStringRes())
        binding.lytHeader.apply {
            tvCostValue.text = summary.cost.toCurrencyMaskedStr()
            tvSaleValue.text = summary.sale.toCurrencyMaskedStr()
            tvGain.text = gainText
            tvGain.setTextColor(gainColor)
            tvGainValue.text = summary.gain.toCurrencyMaskedStr()
            tvGainValue.setTextColor(gainColor)
            tvGain.isVisible = isChecked
            tvGainValue.isVisible = isChecked
            scActive.isChecked = isChecked
        }
    }

    private fun onItemEdit(item: ProductModel) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(ITEM_NAME, item)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun showResultsMessage(isListEmpty: Boolean) {
        with(binding) {
            tvMessage.isVisible = isListEmpty
            rvProducts.isVisible = !isListEmpty
            tvMessage.text = getString(R.string.empty_results_message)
        }
    }

    companion object {
        const val ITEM_NAME = "ITEM"
        const val REQUEST_CODE = 935847621
        fun newInstance() = ProductsFragment()
    }
}