package com.example.newsapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.CategoryCardBinding

class CategoryAdapter(var categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val viewBinding = CategoryCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categories = categoryList[position]
        holder.bind(categories)

        onItemClickListener?.let {
            holder.itemBinding.itemCardView.setOnClickListener {
                onItemClickListener?.onItemClick(position, categories)
            }
        }

    }

    override fun getItemCount(): Int = categoryList.size


    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(position: Int, categories: Category)
    }

    class ViewHolder(var itemBinding: CategoryCardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(categories: Category) {
            itemBinding.category = categories
            itemBinding.invalidateAll()
        }
    }
}