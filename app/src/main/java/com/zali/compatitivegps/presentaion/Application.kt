package com.zali.compatitivegps.presentaion

import android.app.Application
import com.tencent.mmkv.MMKV




class Application : Application() {


    override fun onCreate() {
        super.onCreate()
        val rootDir = MMKV.initialize(this)
        println("mmkv root: $rootDir")
    }
}