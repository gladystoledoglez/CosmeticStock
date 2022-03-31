package com.personal.cosmeticstock.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.personal.cosmeticstock.DetailsActivity
import com.personal.cosmeticstock.MainActivity
import com.personal.cosmeticstock.ProductsListAdapter
import com.personal.cosmeticstock.databinding.FragmentProductsBinding
import com.personal.cosmeticstock.extensions.orFalse
import com.personal.cosmeticstock.extensions.orZero
import com.personal.cosmeticstock.models.ProductModel
import com.personal.cosmeticstock.models.TotalModel
import com.personal.cosmeticstock.repositories.ProductsRepository
import com.personal.cosmeticstock.viewModels.ProductsViewModel

class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var viewModel: ProductsViewModel
    private lateinit var mainActivity: MainActivity

    private val adapter by lazy {
        ProductsListAdapter(::onItemEdit, viewModel::activeProduct, viewModel::deleteProduct)
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
            products.observe(viewLifecycleOwner) {
                adapter.submitList(it.products) { getTotals() }
            }
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
        val gainColor = requireContext().getColor(summary.getGainColorRes(isChecked))
        val gainText = requireContext().getString(summary.getGainStringRes())
        binding.lytHeader.apply {
            tvCostValue.text = summary.cost.orZero().toString()
            tvSaleValue.text = summary.sale.orZero().toString()
            tvGain.text = gainText
            tvGain.setTextColor(gainColor)
            tvGainValue.text = summary.gain.toString()
            tvGainValue.setTextColor(gainColor)
            scActive.isChecked = isChecked
        }
    }

    private fun onItemEdit(item: ProductModel) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra(ITEM_NAME, item)
        startActivityForResult(intent, REQUEST_CODE)
    }

    companion object {
        const val ITEM_NAME = "ITEM"
        const val REQUEST_CODE = 935847621
        fun newInstance() = ProductsFragment()
    }
}