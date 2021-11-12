package com.avc.advanager.di

import android.content.Context
import androidx.room.Room
import com.avc.advanager.data.source.local.AdvanagerDao
import com.avc.advanager.data.source.local.AdvanagerDatabase
import com.avc.advanager.data.source.remote.RetrofitApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val API_TIMEOUT: Long = 3000
private var BASE_URL = "http://192.168.1.168/apis/androvideo/"

/**
 *
 * Build the Moshi object that Retrofit will be using
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
//KotlinJsonAdapterFactory把字串轉成資料

/**
 *
 * Build the OkHttpClient object that Retrofit will be using
 */
private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
    .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
    .build()
//把HttpLoggingInterceptor.Level.BASIC改成HttpLoggingInterceptor.Level.BODY


fun checkRetrofitUpdateRequired(newIP: String) {
    val newUrl = "http://$newIP/apis/androvideo/"
    if (BASE_URL != newUrl) {
        BASE_URL = newUrl
    }
}

@Module
class AppModule {

    @Singleton
    @Provides
    fun providePupilApi(): RetrofitApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RetrofitApiService::class.java)
    }



    @Singleton
    @Provides
    fun provideDb(context: Context): AdvanagerDatabase {
        return Room
            .databaseBuilder(context, AdvanagerDatabase::class.java, "AdvanagerDb")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(db: AdvanagerDatabase): AdvanagerDao {
        return db.advanagerDao()
    }

    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}