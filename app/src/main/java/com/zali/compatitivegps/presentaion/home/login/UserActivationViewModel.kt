package com.zali.compatitivegps.presentaion.home.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zali.compatitivegps.domain.Code
import com.zali.compatitivegps.domain.ContentUserActivation
import com.zali.compatitivegps.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class UserActivationViewModel : ViewModel() {

    private  val TAG = "UserActivationViewModel"

    lateinit var userActivationLiveData: MutableLiveData<Unit>

    fun requestUserActivation(code : Code) : MutableLiveData<Unit>{
        userActivationLiveData = MutableLiveData()

        webService(code)
        return userActivationLiveData
    }

    private fun webService(code: Code) {
        ApiClient.createIService().userActivation(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<Unit>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError UserActivationViewModel: ")
                }

                override fun onComplete() {
                }

                override fun onNext(t: Unit) {
                    Log.d(TAG, "onNext UserActivationViewModel: ")
                    userActivationLiveData.value = t
                }

            })
    }
}