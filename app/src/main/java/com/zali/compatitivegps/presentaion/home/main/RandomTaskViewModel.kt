package com.zali.compatitivegps.presentaion.home.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zali.compatitivegps.domain.ContentTask
import com.zali.compatitivegps.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class RandomTaskViewModel :ViewModel(){

    private  val TAG = "RandomTaskViewModel"

    private lateinit var randomLiveData : MutableLiveData<ContentTask>

    fun requestRandomTask(token:String,id : Int):MutableLiveData<ContentTask>{
        randomLiveData = MutableLiveData()

        webService(token,id)

        return randomLiveData
    }

    private fun webService(token:String,id : Int) {
        ApiClient.createIService().getRandomTask(token,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ContentTask>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    val test = e.message
                    Log.d(TAG, "onError: ".plus(test))
                }

                override fun onComplete() {

                }

                override fun onNext(t: ContentTask) {
                    randomLiveData.value = t
                    Log.d(TAG, "onNext: ")
                }

            })
    }
}