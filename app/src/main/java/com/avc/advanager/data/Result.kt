package com.avc.advanager.data

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Fail(val error: String) : Result<Nothing>()
    data class Error<out T>(val errorMessage: String, val exception: Exception = Exception()) :
        Result<T>()
    data class Loading<out T>(val progress: Int = 0) : Result<T>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$data]"
            is Fail -> "Fail[error=$error]"
            is Error -> "Error[errorMessage=$errorMessage, exception=$exception]"
            is Loading -> "Loading[progress=$progress]"
        }
    }
}

/**
 * `true` if [Result] is of catalogType [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null