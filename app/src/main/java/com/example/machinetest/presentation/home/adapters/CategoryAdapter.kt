package com.example.machinetest.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.machinetest.R
import com.example.machinetest.databinding.ItemCategoryBinding
import com.example.machinetest.domain.model.Category

class CategoryAdapter(private val productClickListener: ChildCategoryAdapter.ProductClickListener) :
    ListAdapter<Category, RecyclerView.ViewHolder>(CategoryDiff) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(getItem(position))
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                val childCategoryAdapter = ChildCategoryAdapter(productClickListener)
                productRecyclerView.adapter = childCategoryAdapter
                childCategoryAdapter.submitList(category.products)
                categoryHeading.text = category.title

                root.setOnClickListener {
                    if (productRecyclerView.isVisible) {
                        clickArrow.setImageDrawable(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_baseline_keyboard_arrow_right_24
                            )
                        )

                    } else {
                        clickArrow.setImageDrawable(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_baseline_keyboard_arrow_down_24
                            )
                        )

                    }
                    noProduct.isVisible =
                        !productRecyclerView.isVisible && category.products.isEmpty()
                    productRecyclerView.isVisible = !productRecyclerView.isVisible
                    divider.isVisible = !divider.isVisible
                }
            }

        }
    }
}

object CategoryDiff : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}