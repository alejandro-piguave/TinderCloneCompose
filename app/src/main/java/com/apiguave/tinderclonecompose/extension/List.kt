package com.apiguave.tinderclonecompose.extension

fun <T> Iterable<T>.filterIndex(index: Int): List<T> {
    return this.filterIndexed{ itemIndex, _ -> itemIndex != index }
}