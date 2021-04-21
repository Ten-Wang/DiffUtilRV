package com.example.diffutilrv.uistate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

typealias StatefulLiveData<T> = LiveData<UiState<T>>
typealias StatefulMutableLiveData<T> = MutableLiveData<UiState<T>>

sealed class UiState<out T> {

    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val throwable: Throwable) : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    object ShowLoading : UiState<Nothing>()
    object HideLoading : UiState<Nothing>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun error(throwable: Throwable = Throwable()) = Error(throwable)
        fun empty() = Empty
        fun showLoading() = ShowLoading
        fun hideLoading() = HideLoading
        fun <T> successOrEmpty(list: List<T>) = if (list.isEmpty()) Empty else Success(list)
    }

}