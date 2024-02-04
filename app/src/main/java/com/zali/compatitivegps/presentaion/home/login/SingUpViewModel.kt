package com.zali.compatitivegps.presentaion.home.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zali.compatitivegps.domain.SingUp
import com.zali.compatitivegps.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class SingUpViewModel : ViewModel() {

    private val TAG = "SingUpViewModel"

    private lateinit var singUpLiveData: MutableLiveData<String>

    fun requestWebserver(singUp: SingUp) : MutableLiveData<String>{

        singUpLiveData = MutableLiveData()

        webserver(singUp)

        return singUpLiveData
    }

    private fun webserver(singUp: SingUp) {
        ApiClient.createIService().createUser(singUp)
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    singUpLiveData.value = e.message
                    Log.d(TAG, "onError: ")
                }

                override fun onComplete() {

                }

                override fun onNext(t: String) {
                    singUpLiveData.value = t
                    Log.d(TAG, "onNext: ")
                }

            })
    }

}