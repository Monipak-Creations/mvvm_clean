package com.example.machinetest.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.machinetest.R
import com.example.machinetest.databinding.FragmentDetailBinding
import com.example.machinetest.domain.model.Product


class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        observeProduct()
    }

    private fun observeProduct() {
        viewModel.product.observe(viewLifecycleOwner) { product ->
            setProductInfo(product)
        }
    }

    private fun setProductInfo(product: Product) = with(binding) {
        productImage.load(product.imageUrl)
        productTitle.text = product.title
        productPrice.text = getString(R.string.rupee, product.price)
        productDescription.text = product.description
    }

}