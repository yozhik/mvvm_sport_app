package com.example.myapplication.ui.common

sealed class UIRequestState<out T> {
    object Idle : UIRequestState<Nothing>()
    object Loading : UIRequestState<Nothing>()
    data class Success<T>(val data: T) : UIRequestState<T>()
    data class Error(val error: String) : UIRequestState<Nothing>()
}