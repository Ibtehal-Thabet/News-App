package com.example.newsapp.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.showMessage
import com.example.newsapp.ui.ViewError
import com.example.newsapp.ui.details.NewsDetailsActivity
import com.example.newsapp.ui.home.news.NewsAdapter
import com.example.newsapp.ui.home.news.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var viewModel: NewsViewModel
    lateinit var viewBinding: FragmentSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        viewBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        initObservers()
        initViews()
    }

    @Inject
    lateinit var newsAdapter: NewsAdapter

    private fun initObservers() {

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            if (it!!.isEmpty()) {
                viewBinding.searchNotFound.visibility = View.VISIBLE
            } else {
                viewBinding.searchNotFound.visibility = View.INVISIBLE
                newsAdapter.bindNews(it)
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            handleError(it)
        }
    }

    private fun initViews() {
        viewBinding.vm = viewModel
        newsAdapter = NewsAdapter()
        viewBinding.searchRecyclerView.adapter = newsAdapter
        newsAdapter.onItemClickListener = itemNewsClick()

        val query = arguments?.getString("query")
        query?.let {
            view?.hideKeyboard()
            viewModel.getSearchedNews(query = it)
            viewModel.shouldShowLoading.postValue(false)
        }
    }

    private fun itemNewsClick(): NewsAdapter.OnItemClickListener {
        return NewsAdapter.OnItemClickListener { news ->
            val intent = Intent(requireContext(), NewsDetailsActivity::class.java)
            intent.putExtra("news", news)
            view?.hideKeyboard()
            startActivity(intent)
        }
    }

    private fun showProgressBar() {
        viewBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        viewBinding.progressBar.visibility = View.INVISIBLE
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun handleError(viewError: ViewError) {
        showMessage(message = viewError.message ?: viewError.throwable?.localizedMessage
        ?: "Something went wrong",
            posActionName = "try again",
            posAction = { dialogInterface, i ->
                dialogInterface.dismiss()
                viewError.onTryAgainClickListener?.onTryAgainClick()
            },
            negActionName = "cancel",
            negAction = { dialogInterface, i ->
                dialogInterface.dismiss()
            })
    }
}