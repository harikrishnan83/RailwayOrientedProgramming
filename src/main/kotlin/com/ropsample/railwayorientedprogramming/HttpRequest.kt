package com.ropsample.railwayorientedprogramming

sealed class Result<T>
data class Success<T>(val message: T) : Result<T>()
data class Failure<T>(val errorMessage: String) : Result<T>()

infix fun <T, U> Result<T>.then(f: (T) -> Result<U>) =
        when (this) {
            is Success -> f(this.message)
            is Failure -> Failure(this.errorMessage)
        }

infix fun <T, U> T.pipeTo(f: (T) -> Result<U>) = Success(this) then f

infix fun <T> Result<T>.error(f: (String) -> Result<T>) =
        if (this is Failure) f(this.errorMessage) else this

class HttpRequest(private val url: String, private val method: HttpMethod, private val body: String) {
    fun matches(anotherRequest: HttpRequest): Any {
        return (anotherRequest pipeTo
                        (this::matchUrl) then
                        (this::matchMethod) then
                        (this::matchBody) error
                        (this::handleError))
    }

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

