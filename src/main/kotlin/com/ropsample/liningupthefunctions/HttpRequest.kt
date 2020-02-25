package com.ropsample.liningupthefunctions

sealed class Result<T>
data class Success<T>(val message: T): Result<T>()
data class Failure<T>(val errorMessage: String): Result<T>()

class HttpRequest(private val url: String, private val method: HttpMethod, private val body: String) {
    fun matches(anotherRequest: HttpRequest): Result<HttpRequest> {
        return when (val matchUrlResult = matchUrl(anotherRequest)) {
            is Failure -> matchUrlResult
            is Success -> {
                when (val matchMethodResult = matchMethod(anotherRequest)) {
                    is Failure -> matchMethodResult
                    is Success -> {
                        matchBody(anotherRequest)
                    }
                }
            }
        }
    }

    private fun matchUrl(anotherRequest: HttpRequest): Result<HttpRequest> {
        if (this.url != anotherRequest.url)
            return Failure("URL did not match. ${this.url} not equal to ${anotherRequest.url}")
        return Success(anotherRequest)
    }

    private fun matchMethod(anotherRequest: HttpRequest): Result<HttpRequest> {
        if (this.body != anotherRequest.body)
            return Failure("Method did not match. ${this.method} not equal to ${anotherRequest.method}")
        return Success(anotherRequest)
    }

    private fun matchBody(anotherRequest: HttpRequest): Result<HttpRequest> {
        if (this.body != anotherRequest.body)
            return Failure("Body did not match. ${this.method} not equal to ${anotherRequest.method}")
        return Success(anotherRequest)
    }
}

sealed class HttpMethod {
    object GET : HttpMethod()
    object POST : HttpMethod()
    object UPDATE : HttpMethod()
    object DELETE : HttpMethod()
}

