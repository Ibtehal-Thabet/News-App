package com.example.newsapp.ui.home.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.api.model.sourcesResponse.Source
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.showMessage
import com.example.newsapp.ui.ViewError
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {

    lateinit var viewBinding: FragmentNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
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
        initViews()
        initObservers()
        viewModel.getNewsSources()
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
    }


    private fun bindTabs(sources: List<Source?>?) {
        if (sources == null)
            return

        sources.forEach { source ->
            val tab = viewBinding.tabLayout.newTab()
            tab.text = source?.name
            tab.tag = source
            viewBinding.tabLayout.addTab(tab)
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

    fun handleError(viewError: ViewError) {
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