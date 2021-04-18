package com.sofit.drinkrecepies.data

data class Resource<out T>(
    val status: Status, val data: T? = null, val message: String = "", val statusCode: Int = 200
) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> error(message: String, statusCode: Int = 0): Resource<T> {
            return Resource(Status.ERROR, message = message, statusCode = statusCode)
        }
    }
}