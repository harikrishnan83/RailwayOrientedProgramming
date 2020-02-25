package com.ropsample.procedural

sealed class Result
data class Success(val message: String): Result()
data class Failure(val errorMessage: String): Result()

class HttpRequest(private val url: String, private val method: HttpMethod, private val body: String) {
    fun matches(anotherRequest: HttpRequest): Result {
        if (this.url != anotherRequest.url)
            return Failure("URL did not match. ${this.url} not equal to ${anotherRequest.url}")
        if (this.method != anotherRequest.method)
            return Failure("Method did not match. ${this.method} not equal to ${anotherRequest.method}")
        if (this.body != anotherRequest.body)
            return Failure("Body did not match. ${this.body} not equal to ${anotherRequest.body}")
        return Success("everything matches")
    }
}

sealed class HttpMethod {
    object GET : HttpMethod()
    object POST : HttpMethod()
    object UPDATE : HttpMethod()
    object DELETE : HttpMethod()
}

