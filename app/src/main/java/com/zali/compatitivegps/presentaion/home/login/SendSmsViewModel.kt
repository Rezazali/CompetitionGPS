package com.zali.compatitivegps.presentaion.home.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zali.compatitivegps.domain.ContentSms
import com.zali.compatitivegps.domain.SendSms
import com.zali.compatitivegps.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SendSmsViewModel : ViewModel() {

    private  val TAG = "SendSmsViewModel"

    private lateinit var smsSendMutableLiveData: MutableLiveData<ContentSms>

    fun requestSmsSend(sendSms: SendSms) : MutableLiveData<ContentSms>{
        smsSendMutableLiveData = MutableLiveData()

        webServer(sendSms)

     return   smsSendMutableLiveData
    }

    private fun webServer(sendSms: SendSms) {
        ApiClient.createIService().sendSms(sendSms)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<ContentSms>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    smsSendMutableLiveData.value = ContentSms("",e.message.toString())
                    Log.d(TAG, "onError: ")
                }

                override fun onComplete() {
                }

                override fun onNext(t: ContentSms) {
                    Log.d(TAG, "onNextSendSmsViewModel: ")
                    smsSendMutableLiveData.value = t
                }

            })
    }
}