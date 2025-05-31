package com.apiguave.auth_ui.extensions

fun String.isValidUsername(): Boolean{
    return this.length in 3..30 && this.all { it.isLetter() }
}