package com.rainbow.services

import UserRepository
import kotlinx.coroutines.processNextEventInCurrentThread
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok

class UserHandler(private val userRepository: UserRepository) {
    suspend fun findOne(request: ServerRequest): ServerResponse {
        val user = userRepository.findOne(request.pathVariable("id"))
        return when {
            user != null -> ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(user)
            else -> notFound().buildAndAwait()
        }
    }

    suspend fun save(request: ServerRequest):ServerResponse {
        val body = request.awaitBody<User>()

        print(body)
        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(userRepository.insert(body))
    }

}