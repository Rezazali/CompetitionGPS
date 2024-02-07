package com.zali.compatitivegps.presentaion.home.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zali.compatitivegps.domain.LogIn
import com.zali.compatitivegps.domain.Token
import com.zali.compatitivegps.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LogInViewModel : ViewModel() {

    private  val TAG = "LogInViewModel"
    private lateinit var logInMutable : MutableLiveData<Token>

    fun requestLogin(logIn: LogIn) : MutableLiveData<Token>{

        logInMutable = MutableLiveData()

        webServer(logIn)

        return logInMutable
    }

    private fun webServer(logIn: LogIn) {
        ApiClient.createIService().login(logIn)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<Token>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    logInMutable.value = Token("",e.message.toString())
                    Log.d(TAG, "onErrorLogIn: ")
                }

                override fun onComplete() {

                }

                override fun onNext(t: Token) {
                    logInMutable.value = t
                    Log.d(TAG, "onNextLogIn: ")
                }

            })
    }

}