package com.team7.joongonawa

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Failed<out T>(val data: T) : Result<T>()
}
