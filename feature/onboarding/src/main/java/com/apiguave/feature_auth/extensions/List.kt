package com.apiguave.feature_auth.extensions

fun <T> Iterable<T>.filterIndex(index: Int): List<T> {
    return this.filterIndexed{ itemIndex, _ -> itemIndex != index }
}