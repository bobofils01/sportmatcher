package com.example.sportmatcher.domain.utils

import java.util.regex.Pattern



fun String?.isEmailValid(): Boolean {
    if(this == null)
        return false

    val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String?.isPasswordValid(): Boolean {
    if(this == null || this.length < 6)
        return false
    val expression = "^(?=.*[0-9])(?=.*[A-Z]).{6,}\$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(this)
    return matcher.matches()
}

fun String?.isNameValid(): Boolean{
    if(this == null || Pattern.compile( "[0-9]" ).matcher(this).find())
        return false

    return true
}
