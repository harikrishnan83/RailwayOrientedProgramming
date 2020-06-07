package com.ropsample

import com.ropsample.arrow.Failure
import com.ropsample.arrow.HttpMethod
import com.ropsample.arrow.HttpRequest
import com.ropsample.arrow.Success
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HttpRequestMatcherArrowTest {
    @Test
    fun `should match requests when url is the same`() {
        val request = HttpRequest("/resources", HttpMethod.GET, "body")
        val anotherRequest = HttpRequest("/resources", HttpMethod.GET, "body")
        request.matches(anotherRequest).let {
            assertThat(it is Success<*>).isTrue()
        }
    }

    @Test
    fun `should return error when request url does not match`() {
        val request = HttpRequest("/resources", HttpMethod.GET, "body")
        val anotherRequest = HttpRequest("/anotherResource", HttpMethod.GET, "body")
        request.matches(anotherRequest).let {
            assertThat(it is Failure<*>).isTrue()
            assertThat((it as Failure<*>).errorMessage)
                    .isEqualTo("URL did not match. /resources not equal to /anotherResource")
        }
    }

    @Test
    fun `should return error when request method does not match`() {
        val request = HttpRequest("/resources", HttpMethod.GET, "body")
        val anotherRequest = HttpRequest("/resources", HttpMethod.POST, "body")
        request.matches(anotherRequest).let {
            assertThat(it is Failure<*>).isTrue()
            assertThat((it as Failure<*>).errorMessage)
                    .isEqualTo("Method did not match. GET not equal to POST")
        }
    }

    @Test
    fun `should return error when body does not match`() {
        val request = HttpRequest("/resources", HttpMethod.GET, "body A")
        val anotherRequest = HttpRequest("/resources", HttpMethod.GET, "body B")
        request.matches(anotherRequest).let {
            assertThat(it is Failure<*>).isTrue()
            assertThat((it as Failure<*>).errorMessage)
                    .isEqualTo("Body did not match. GET not equal to GET")
        }

    }
}