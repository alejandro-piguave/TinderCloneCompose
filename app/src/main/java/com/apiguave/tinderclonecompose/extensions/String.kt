package com.apiguave.tinderclonecompose.extensions

fun String.isValidUsername(): Boolean{
    return this.length in 3..30 && this.all { it.isLetter() }
}