package com.zali.compatitivegps.domain

import retrofit2.http.Field

data class SingUp (
    var email : String,
    var nickName : String,
    var password : String,
    var phone : String
)