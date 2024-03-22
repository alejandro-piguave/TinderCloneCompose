package com.apiguave.tinderclonecompose.extension

fun String.isValidUsername(): Boolean{
    return this.length in 3..30 && this.all { it.isLetter() }
}