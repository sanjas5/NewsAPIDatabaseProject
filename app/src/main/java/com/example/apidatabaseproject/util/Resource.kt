package com.example.apidatabaseproject.util

sealed class Resource<T>(
    val data: T? = null,  //body of our response
    val message: String? = null //could be the error message for example
) {

    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}