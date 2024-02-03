package com.zali.compatitivegps.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var instance : Retrofit? = null


    fun getInstance() : Retrofit{
        if (instance!=null){
            synchronized(ApiClient::class.java){
                if (instance != null){
                    instance = Retrofit.Builder()
                        .baseUrl("")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()
                }
            }
        }
        return instance!!
    }

}