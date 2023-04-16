package com.example.machinetest.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.example.machinetest.R
import com.example.machinetest.databinding.FragmentHomeBinding
import com.example.machinetest.domain.model.Category
import com.example.machinetest.domain.model.Product
import com.example.machinetest.presentation.home.adapters.CategoryAdapter
import com.example.machinetest.presentation.home.adapters.ChildCategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ChildCategoryAdapter.ProductClickListener{

    private lateinit var binding : FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()
    private val categoryAdapter by lazy { CategoryAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.recyclerView.adapter = categoryAdapter
        observeCategoriesState()
    }


    private fun observeCategoriesState() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.homeUiState.collectLatest {
                when (it) {
                    is HomeUiState.Loading -> handleLoading(showLoading = true)
                    is HomeUiState.Success -> handleSuccess(it.categories)
                    is HomeUiState.Error -> handleError(it.message)
                    else -> Unit
                }
            }
        }
    }

    private fun handleLoading(showLoading: Boolean, isError: Boolean = false) = with(binding) {
        progressBar.isVisible = showLoading
        recyclerView.isVisible = !showLoading && !isError
    }

    private fun handleSuccess(categories: List<Category>) {
        handleLoading(showLoading = false)
        categoryAdapter.submitList(categories)
        val animation = AnimationUtils.loadLayoutAnimation(
            requireContext(),
            R.anim.layout_animation_from_bottom
        )
        binding.recyclerView.layoutAnimation = animation
        binding.recyclerView.scheduleLayoutAnimation()
    }

    private fun handleError(message: String) {
        handleLoading(showLoading = false, isError = true)
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).setAction(
            getString(R.string.retry)
        ) { viewModel.retry() }.show()
    }



    override fun onClick(product: Product) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(product))
    }

}