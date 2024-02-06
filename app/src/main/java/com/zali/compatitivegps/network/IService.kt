package com.zali.compatitivegps.network

import com.zali.compatitivegps.domain.Code
import com.zali.compatitivegps.domain.ContentSms
import com.zali.compatitivegps.domain.ContentUserActivation
import com.zali.compatitivegps.domain.LogIn
import com.zali.compatitivegps.domain.SendSms
import com.zali.compatitivegps.domain.SingUp
import com.zali.compatitivegps.domain.Token
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.PartMap

interface IService {
    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("users")
    fun createUser(
        @Body user: SingUp
    ): Observable<String>


    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("user/login")
    fun login(
        @Body logIn: LogIn
    ) : Observable<Token>



    @Headers("Accept: application/json", "Content-Type: application/json")
    @POST("user/smsCode")
    fun sendSms(
        @Body sms: SendSms
    ):Observable<ContentSms>

    @Headers("Accept: application/json", "Content-Type: application/json")
    @PUT("user/activation")
    fun userActivation(
        @Body code: Code
    ):Observable<Unit>


}
