package com.zali.compatitivegps.util

import android.content.Context
import android.widget.Toast
import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText

// Extension function to check if EditText is not empty
fun AppCompatEditText.isNotEmpty(): Boolean {
    return this.text.toString().trim().isNotEmpty()
}

// Extension function to validate email
fun AppCompatEditText.isValidEmail(): Boolean {
    return this.text.toString().trim().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex())
}

// Extension function to validate mobile number
fun AppCompatEditText.isValidMobile(): Boolean {
    return this.text.toString().trim().matches("^\\d{10}$".toRegex())
}

// Extension function to compare text of two EditTexts
fun AppCompatEditText.textMatches(other: EditText): Boolean {
    return this.text.toString().trim() == other.text.toString().trim()
}

fun Context.showToast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
}

fun EditText.content() = this.text.toString()