package com.zali.compatitivegps.network

import com.zali.compatitivegps.domain.SingUp
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface IService {



    @Headers("Accept:application/json")
    @FormUrlEncoded
    @POST("users")
    fun createUser(
        @Body singUp: SingUp
    ) : Observable<String>


}