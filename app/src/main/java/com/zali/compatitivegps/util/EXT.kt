package com.zali.compatitivegps.util

import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.validate(predicate: (String) -> Boolean, errorMessage: String) {
    val isValid = predicate(this.editText?.text.toString())
    this.error = if (isValid) null else errorMessage
}

fun EditText.content() = this.text.toString()