package com.example.newsapp.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {
    lateinit var viewBinding: FragmentCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    var adapter = CategoryAdapter(Category.getCategoryList())
    private fun initViews() {
        viewBinding.recyclerView.adapter = adapter
        adapter.onItemClickListener =
            CategoryAdapter.OnItemClickListener { position, categories ->
                onItemClickListener?.onItemClick(categories)
            }
    }

    var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(categories: Category)
    }

}