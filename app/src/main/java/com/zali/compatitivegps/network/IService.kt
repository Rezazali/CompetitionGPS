package com.zali.compatitivegps.network

import com.zali.compatitivegps.domain.Code
import com.zali.compatitivegps.domain.ContentSms
import com.zali.compatitivegps.domain.ContentTask
import com.zali.compatitivegps.domain.LogIn
import com.zali.compatitivegps.domain.SendSms
import com.zali.compatitivegps.domain.SingUp
import com.zali.compatitivegps.domain.Token
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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


    @Headers("Accept: application/json","Accept: application/json")
    @GET("tasks/{taskId}")
    fun getRandomTask(@Header("token") token : String,@Path("taskId") id:Int) :Observable<ContentTask>


}
