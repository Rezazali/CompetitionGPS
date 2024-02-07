package com.zali.compatitivegps.domain

data class SendSms(
    var countryCode : String,
    var phone : String
)

data class ContentSms(
    var content : String,
    var errorMessage : String
)

data class Code(
    var code : String
)
data class ContentUserActivation(
    var content: String
)