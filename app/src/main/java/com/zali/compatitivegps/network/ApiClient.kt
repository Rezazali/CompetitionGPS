package com.zali.compatitivegps.network

import com.zali.compatitivegps.util.Constants
import com.zali.compatitivegps.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var instance : Retrofit? = null
    private fun getInstance() : Retrofit{
        if (instance!=null){
            synchronized(ApiClient::class.java){
                if (instance != null){
                    instance = Retrofit.Builder()
                        .baseUrl(BASE_URL)
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