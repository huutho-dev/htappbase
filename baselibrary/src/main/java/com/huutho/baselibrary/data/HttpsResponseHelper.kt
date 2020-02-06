package com.huutho.baselibrary.data

import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData

data class Resource<out T : Entity>(val status: Status, val data: T?, val e: Exception?) {
    companion object {
        fun loading() = Resource(Status.LOADING, null, null)

        fun <T : Entity> success(data: T): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun failure(e: Exception) = Resource(Status.FAILURE, null, e)

        fun complete() = Resource(Status.COMPLETE, null, null)
    }
}


suspend fun <T : Entity> execute(source: T): Resource<T> {
    return try {
        Resource.success(source)
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.failure(e)
    }
}


suspend fun <T : Entity> LiveDataScope<Resource<T>>.emitDefault(source: T) {
    emit(Resource.loading())
    emit(execute(source))
    emit(Resource.complete())
}


fun <T : Entity> handleDataDefault(
    dataResponse: Resource<T>,
    onLoading: () -> Unit,
    onSuccess: (data: T?) -> Unit,
    onFailure: (ex: Exception?) -> Unit,
    onComplete: () -> Unit
) {
    when (dataResponse.status) {
        Status.LOADING -> onLoading.invoke()
        Status.SUCCESS -> onSuccess.invoke(dataResponse.data)
        Status.FAILURE -> onFailure.invoke(dataResponse.e)
        Status.COMPLETE -> onComplete.invoke()
    }
}


fun <T : Entity> callApiDefault(
    liveData: MutableLiveData<Resource<T>>,
    source: T,
    loadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false),
    onSuccess: (data: T) -> Unit = {},
    onFailure: (e: Exception) -> Unit = {},
    onComplete: () -> Unit = {}
) {
    liveData.value = Resource.loading()
    loadingLiveData.value = true
    try {
        liveData.value = Resource.success(source)
        onSuccess.invoke(source)
    } catch (e: Exception) {
        e.printStackTrace()
        liveData.value = Resource.failure(e)
        onFailure.invoke(e)
    }

    onComplete.invoke()
    liveData.value = Resource.complete()
    loadingLiveData.value = false
}