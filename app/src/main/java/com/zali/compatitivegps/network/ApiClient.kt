package com.zali.compatitivegps.network

import com.zali.compatitivegps.util.Constants
import com.zali.compatitivegps.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        level = HttpLoggingInterceptor.Level.HEADERS
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private var instance : Retrofit? = null
    private fun getInstance() : Retrofit{
        if (instance == null){
            synchronized(ApiClient::class.java){
                if (instance == null){
                    instance = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()
                }
            }
        }
        return instance!!
    }

    private val iService : IService = getInstance().create(IService::class.java)


    fun createIService() : IService{
        return iService
    }
}