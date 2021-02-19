package com.jordanro.stackoverflow.di

import android.content.Context
import com.jordanro.stackoverflow.data.local.AppDatabase
import com.jordanro.stackoverflow.data.local.QuestionsDao
import com.jordanro.stackoverflow.data.remote.StackOverflowService
import com.jordanro.stackoverflow.repositories.QuestionsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    val BASE_URL = "https://api.stackexchange.com/2.2/"

    @Singleton
    @Provides
    fun provideHttpClient(@ApplicationContext appContext: Context) : OkHttpClient =
        OkHttpClient().newBuilder()
            .cache(Cache(appContext.cacheDir, 10 * 1024 * 1024.toLong()))
//            .addInterceptor(IMDBRequestInterceptor(API_KEY,86400))
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideStackOverflowService(retrofit: Retrofit) : StackOverflowService = retrofit.create(StackOverflowService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)


    @Singleton
    @Provides
    fun provideQuestionsRepository(remoteDataSource: StackOverflowService,
                          db: AppDatabase) : QuestionsRepository =
        QuestionsRepository(remoteDataSource, db)
}