package com.zali.compatitivegps.domain

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.http.Field

data class SingUp (
    var email : String,
    var nickName : String,
    var password : String,
    var phone : String
){
    fun toPartMap(): Map<String,RequestBody>{
        return mapOf(
            "email" to RequestBody.create("text/plain".toMediaTypeOrNull(), email),
            "nickName" to RequestBody.create("text/plain".toMediaTypeOrNull(),nickName),
            "password" to RequestBody.create("text/plain".toMediaTypeOrNull(), password),
            "phone" to RequestBody.create("text/plain".toMediaTypeOrNull(),phone)
        )
    }
}