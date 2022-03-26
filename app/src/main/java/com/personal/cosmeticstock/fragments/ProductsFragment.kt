package com.personal.cosmeticstock.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.personal.cosmeticstock.DetailsActivity
import com.personal.cosmeticstock.MainActivity
import com.personal.cosmeticstock.ProductsListAdapter
import com.personal.cosmeticstock.databinding.FragmentProductsBinding
import com.personal.cosmeticstock.enums.ChangeListType
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
        ProductsListAdapter(::onItemEdit, ::onItemActive, viewModel::deleteProduct)
    }
    private var changeListType = ChangeListType.NONE
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
    }

    override fun onDestroy() {
        super.onDestroy()
        rvProducts?.adapter = null
    }

    private fun initObservers() {
        with(viewModel) {
            products.observe(viewLifecycleOwner) {
                getTotals()
                adapter.submitList(it.products) { setupListChanges(it.index) }
            }
            listProducts()
            totals.observe(viewLifecycleOwner) { setupHeader(it) }
            getTotals()
        }
    }

    private fun initListeners() {
        with(binding) {
            lytHeader.scActive.apply {
                setOnClickListener {
                    viewModel.activeProductsList(isChecked)
                    changeListType = ChangeListType.ALL
                }
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

    private fun setupListChanges(index: Int) {
        when (changeListType) {
            ChangeListType.ALL -> adapter.notifyItemRangeChanged(index, adapter.itemCount)
            ChangeListType.CURRENT -> adapter.notifyItemChanged(index)
            else -> {}
        }
        changeListType = ChangeListType.NONE
    }

    private fun onItemEdit(item: ProductModel) {
        val intent = Intent(context, DetailsActivity::class.java)
        intent.putExtra("ITEM", item)
        startActivity(intent)
    }

    private fun onItemActive(item: ProductModel) {
        viewModel.activeProduct(item)
        changeListType = ChangeListType.CURRENT
    }

    companion object {
        fun newInstance() = ProductsFragment()
    }
}