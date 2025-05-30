package com.ropsample.railwayorientedprogramming

sealed class Result<T>
data class Success<T>(val value: T) : Result<T>()
data class Failure<T>(val error: String) : Result<T>()

infix fun <T, U> Result<T>.then(f: (T) -> Result<U>) =
        when (this) {
            is Success -> f(this.value)
            is Failure -> Failure(this.error)
        }

infix fun <T, U> T.pipeTo(f: (T) -> Result<U>) = Success(this) then f

infix fun <T> Result<T>.error(f: (String) -> Result<T>) =
        if (this is Failure) f(this.error) else this

class HttpRequest(private val url: String, private val method: HttpMethod, private val body: String) {
    fun matches(anotherRequest: HttpRequest) =
            anotherRequest pipeTo
                    ::matchUrl then
                    ::matchMethod then
                    ::matchBody error
                    ::handleError

    private fun matchUrl(anotherRequest: HttpRequest): Result<HttpRequest> {
        if (this.url != anotherRequest.url)
            return Failure("URL did not match. ${this.url} not equal to ${anotherRequest.url}")
        return Success(anotherRequest)
    }

    private fun matchMethod(anotherRequest: HttpRequest): Result<HttpRequest> {
        if (this.method != anotherRequest.method)
            return Failure("Method did not match. ${this.method} not equal to ${anotherRequest.method}")
        return Success(anotherRequest)
    }

    private fun matchBody(anotherRequest: HttpRequest): Result<HttpRequest> {
        if (this.body != anotherRequest.body)
            return Failure("Body did not match. ${this.method} not equal to ${anotherRequest.method}")
        return Success(anotherRequest)
    }

    private fun handleError(errorMessage: String): Result<HttpRequest> {
        return Failure(errorMessage)
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

