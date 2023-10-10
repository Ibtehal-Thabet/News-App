package com.example.newsapp.data.repository

import com.example.newsapp.data.dataSource.NewsOfflineDataSourceImpl
import com.example.newsapp.data.dataSource.NewsOnlineDataSourceImpl
import com.example.newsapp.data.dataSource.SourcesOnlineDataSourceImpl
import com.example.newsapp.dataSource.NewsDataSource
import com.example.newsapp.dataSource.SourcesDataSource
import com.example.newsapp.repository.news.NewsRepository
import com.example.newsapp.repository.sourcesRepository.SourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun provideSourcesRepository(repo: SourcesRepositoryImpl): SourcesRepository

    @Binds
    abstract fun provideSourcesDataSource(dataSource: SourcesOnlineDataSourceImpl): SourcesDataSource

    @NewsOnlineDataSource
    @Binds
    abstract fun provideNewsDataSource(newsDataSource: NewsOnlineDataSourceImpl): NewsDataSource

    @NewsOfflineDataSource
    @Binds
    abstract fun provideNewsOfflineDataSource(newsDataSource: NewsOfflineDataSourceImpl): NewsDataSource

    @Binds
    abstract fun provideNewsRepository(repo: NewsRepositoryImpl): NewsRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsOfflineDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NewsOnlineDataSource