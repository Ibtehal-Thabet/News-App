package com.example.newsapp.ui.home.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.api.model.sourcesResponse.Source
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.showMessage
import com.example.newsapp.ui.ViewError
import com.example.newsapp.ui.categories.Category
import com.example.newsapp.ui.details.NewsDetailsActivity
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {

    lateinit var viewBinding: FragmentNewsBinding
    lateinit var viewModel: NewsViewModel
    lateinit var category: Category

    var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentNewsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        initViews()
        initObservers()
        viewModel.getNewsSources(category)
    }

    companion object {
        fun getInstance(category: Category): NewsFragment {
            val newsFragment = NewsFragment()
            newsFragment.category = category
            return newsFragment
        }
    }

    private fun initObservers() {
        viewModel.sourcesLiveData.observe(viewLifecycleOwner) { sources ->
            bindTabs(sources)
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            adapter.bindNews(it)
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            handleError(it)
        }
    }

    @Inject
    lateinit var adapter: NewsAdapter
    private fun initViews() {
        viewBinding.vm = viewModel
        viewBinding.lifecycleOwner = this
        viewBinding.recyclerView.adapter = adapter
        adapter.onItemClickListener = itemNewsClick()
    }

    lateinit var source: Source
    fun getNews() {
        viewModel.getNews(source.id)
        loading = false
    }

    fun getSearchedNews(query: String?) {
        viewModel.getSearchedNews(query ?: "")
        loading = false
    }

    private fun itemNewsClick(): NewsAdapter.OnItemClickListener {
        return NewsAdapter.OnItemClickListener { news ->
            val intent = Intent(requireContext(), NewsDetailsActivity::class.java)
            intent.putExtra("news", news)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        viewBinding.recyclerView
            .addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastVisibleItemCount = layoutManager.findLastVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount
                    val visibleThreshold = 3
                    if (!loading && totalItemCount - lastVisibleItemCount <= visibleThreshold) {
                        loading = true
                        getNews()
                    }

                }
            })
    }

    fun bindTabs(sources: List<Source?>?) {
        if (sources == null)
            return

        sources.forEach { source ->
            val tab = viewBinding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source
            viewBinding.tabLayout.addTab(tab)
            val layoutParams = LinearLayout.LayoutParams(tab.view.layoutParams)
            layoutParams.marginStart = 12
            layoutParams.marginEnd = 12
            layoutParams.topMargin = 18
            layoutParams.bottomMargin = 12
            tab.view.layoutParams = layoutParams

        }
        viewBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    viewModel.getNews(source.id)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as Source
                    viewModel.getNews(source.id)
                }
            }
        )
        viewBinding.tabLayout.getTabAt(0)?.select()
    }

    private fun handleError(viewError: ViewError) {
        showMessage(
            message = viewError.message ?: viewError.throwable?.localizedMessage
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