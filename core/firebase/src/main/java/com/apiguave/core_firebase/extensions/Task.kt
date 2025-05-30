package com.apiguave.core_firebase.extensions

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <TResult> Task<TResult>.getTaskResult(): TResult {
    return suspendCancellableCoroutine { continuation ->
        this.addOnCompleteListener { task ->
            if(task.isSuccessful){
                continuation.resume(task.result)
            } else {
                continuation.resumeWithException(task.exception!!)
            }
        }
    }
}

