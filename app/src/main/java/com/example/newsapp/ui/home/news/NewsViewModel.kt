package com.example.newsapp.ui.home.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.model.newsResponse.News
import com.example.newsapp.data.api.model.newsResponse.NewsResponse
import com.example.newsapp.data.api.model.sourcesResponse.Source
import com.example.newsapp.data.api.model.sourcesResponse.SourcesResponse
import com.example.newsapp.data.repository.NewsOfflineDataSource
import com.example.newsapp.dataSource.NewsDataSource
import com.example.newsapp.repository.news.NewsRepository
import com.example.newsapp.repository.sourcesRepository.SourcesRepository
import com.example.newsapp.ui.ViewError
import com.example.newsapp.ui.categories.Category
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val sourcesRepo: SourcesRepository,
    private val newsRepository: NewsRepository,
    @NewsOfflineDataSource
    private val newsOfflineDataSource: NewsDataSource
) : ViewModel() {

    val shouldShowLoading = MutableLiveData<Boolean>()
    val sourcesLiveData = MutableLiveData<List<Source?>?>()
    val newsLiveData = MutableLiveData<List<News?>?>()
    val errorLiveData = MutableLiveData<ViewError>()

    fun getNewsSources(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            shouldShowLoading.postValue(true)

            try {
                val sources = sourcesRepo.getSources(category)
                sourcesLiveData.postValue(sources)
            } catch (e: HttpException) {
                val errorBodyJsonString = e.response()?.errorBody()?.string()
                val response = Gson().fromJson(errorBodyJsonString, SourcesResponse::class.java)
                errorLiveData.postValue(
                    ViewError(
                        message = response.message
                    ) {
                        getNewsSources(category)
                    })

            } catch (e: Exception) {
                errorLiveData.postValue(
                    ViewError(
                        throwable = e
                    ) {
                        getNewsSources(category)
                    })
            } finally {
                shouldShowLoading.postValue(false)
            }
        }
    }

    fun getNews(id: String?) {
        shouldShowLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val articles = newsRepository.getNews(id ?: "")
                newsLiveData.postValue(articles)

            } catch (e: HttpException) {
                val errorBodyJsonString = e.response()?.errorBody()?.string()
                val response = Gson().fromJson(errorBodyJsonString, NewsResponse::class.java)
                errorLiveData.postValue(ViewError(message = response.message) {
                    getNews(id)
                })
            } catch (e: Exception) {
                errorLiveData.postValue(
                    ViewError(
                        throwable = e
                    ) {
                        getNews(id)
                    })
            } finally {
                shouldShowLoading.postValue(false)
            }
        }
    }

    fun getSearchedNews(query: String?) {
        shouldShowLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val articles = newsRepository.getSearchedNews(query ?: "")
                newsLiveData.postValue(articles)

            } catch (e: HttpException) {
                val errorBodyJsonString = e.response()?.errorBody()?.string()
                val response = Gson().fromJson(errorBodyJsonString, NewsResponse::class.java)
                errorLiveData.postValue(ViewError(message = response.message) {
                    getSearchedNews(query)
                })
            } catch (e: Exception) {
                errorLiveData.postValue(
                    ViewError(
                        throwable = e
                    ) {
                        getSearchedNews(query)
                    })
            } finally {
                shouldShowLoading.postValue(false)
            }
        }
    }
}