package com.example.machinetest.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.machinetest.R
import com.example.machinetest.databinding.ItemProductBinding
import com.example.machinetest.domain.model.Product


class ChildCategoryAdapter(private val productClickListener: ProductClickListener) :
    ListAdapter<Product, RecyclerView.ViewHolder>(ProductDiff) {

    interface ProductClickListener {
        fun onClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ProductViewHolder).bind(getItem(position))
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                productImage.load(product.imageUrl)
                productName.text = product.title
                productPrice.text = itemView.context.getString(R.string.rupee, product.price)
                root.setOnClickListener {
                    productClickListener.onClick(product)
                }
            }
        }
    }
}

object ProductDiff : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}