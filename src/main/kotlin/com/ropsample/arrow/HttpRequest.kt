package com.ropsample.arrow

import arrow.core.Either
import arrow.core.flatMap

sealed class Result<T>
data class Success<T>(val message: T) : Result<T>()
data class Failure<T>(val errorMessage: T) : Result<T>()

class HttpRequest(private val url: String, private val method: HttpMethod, private val body: String) {
    fun matches(anotherRequest: HttpRequest) =
            matchAnotherRequest(anotherRequest)

    private fun matchAnotherRequest(anotherRequest: HttpRequest) =
            matchUrl(anotherRequest).flatMap {
                matchMethod(it)
            }.flatMap {
                matchBody(it)
            }.fold({ Failure(it) }, { Success(it) })

    private fun matchUrl(anotherRequest: HttpRequest): Either<String, HttpRequest> {
        if (this.url != anotherRequest.url)
            return Either.Left("URL did not match. ${this.url} not equal to ${anotherRequest.url}")
        return Either.Right(anotherRequest)
    }

    private fun matchMethod(anotherRequest: HttpRequest): Either<String, HttpRequest> {
        if (this.method != anotherRequest.method)
            return Either.Left("Method did not match. ${this.method} not equal to ${anotherRequest.method}")
        return Either.Right(anotherRequest)
    }

    private fun matchBody(anotherRequest: HttpRequest): Either<String, HttpRequest> {
        if (this.body != anotherRequest.body)
            return Either.Left("Body did not match. ${this.method} not equal to ${anotherRequest.method}")
        return Either.Right(anotherRequest)
    }

}

sealed class HttpMethod {
    object GET : HttpMethod() {
        override fun toString(): String {
            return "GET"
        }
    }

    object POST : HttpMethod() {
        override fun toString(): String {
            return "POST"
        }
    }
}

