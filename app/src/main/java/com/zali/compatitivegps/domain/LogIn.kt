package com.zali.compatitivegps.domain

data class LogIn(
    var email : String,
    var password : String
)

data class Token(
    var token : String,
    var errorMessage : String
)