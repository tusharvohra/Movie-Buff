package com.tusharvohra.moviebuff.di.modules

import android.content.Context
import com.tusharvohra.moviebuff.data.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton


/**
 * Created by tusharvohra on 3/12/20.
 */
@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    private const val cacheSize = 10 * 1024 * 1024

    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache {
        var cache: Cache? = null
        try {
            cache = Cache(File(context.cacheDir, "http-cache"), cacheSize.toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cache!!
    }

//    @Provides
//    fun provideOnlineInterceptor(@ApplicationContext context: Context): Interceptor {
//        return Interceptor { chain ->
//            val response: Response = chain.proceed(chain.request())
//            val cacheControl: CacheControl =
//                if (javax.swing.text.Utilities.isNetworkConnected(context)) {
//                    OkHttpClient.Builder().maxAge(0, TimeUnit.SECONDS).build()
//                } else {
//                    OkHttpClient.Builder()
//                        .maxStale(7, TimeUnit.DAYS)
//                        .build()
//                }
//            response.newBuilder()
//                .removeHeader(HEADER_PRAGMA)
//                .removeHeader(HEADER_CACHE_CONTROL)
//                .header(HEADER_CACHE_CONTROL, cacheControl.toString())
//                .build()
//        }
//    }

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .cache(provideCache(context))
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}