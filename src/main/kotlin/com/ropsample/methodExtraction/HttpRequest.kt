package com.ropsample.methodExtraction

sealed class Result
data class Success(val message: String): Result()
data class Failure(val errorMessage: String): Result()

class HttpRequest(private val url: String, private val method: HttpMethod, private val body: String) {
    fun matches(anotherRequest: HttpRequest): Result {
        when (val result = matchUrl(anotherRequest)) {
            is Failure -> return result
        }
        when (val result = matchMethod(anotherRequest)) {
            is Failure -> return result
        }
        when (val result = matchBody(anotherRequest)) {
            is Failure -> return result
        }
        return Success("everything matches")
    }

    private fun matchUrl(anotherRequest: HttpRequest): Result {
        if (this.url != anotherRequest.url)
            return Failure("URL did not match. ${this.url} not equal to ${anotherRequest.url}")
        return Success("Url matches")
    }

    private fun matchMethod(anotherRequest: HttpRequest): Result {
        if (this.body != anotherRequest.body)
            return Failure("Method did not match. ${this.method} not equal to ${anotherRequest.method}")
        return Success("Method matches")
    }

    private fun matchBody(anotherRequest: HttpRequest): Result {
        if (this.body != anotherRequest.body)
            return Failure("Body did not match. ${this.method} not equal to ${anotherRequest.method}")
        return Success("Body matches")
    }
}

sealed class HttpMethod {
    object GET : HttpMethod()
    object POST : HttpMethod()
    object UPDATE : HttpMethod()
    object DELETE : HttpMethod()
}

