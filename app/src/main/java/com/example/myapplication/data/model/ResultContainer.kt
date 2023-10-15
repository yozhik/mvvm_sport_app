package com.example.myapplication.data.model

sealed class ResultContainer<out T> {
    data class Success<T>(val data: T) : ResultContainer<T>()
    data class Error(val error: String) : ResultContainer<Nothing>()
}
