package com.rainbow.services

import CourseRepository
import UserRepository
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

    suspend fun findAll(request: ServerRequest): ServerResponse {
        return ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(userRepository.findAll())
    }

    suspend fun save(request: ServerRequest):ServerResponse {
        val body = request.awaitBody<User>()

        print(body)
        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(userRepository.insert(body))
    }

}

class CourseHandler(private val courseRepository: CourseRepository) {
    suspend fun findOne(request: ServerRequest): ServerResponse {
        val course = courseRepository.findOne(request.pathVariable("id"))
        return when {
            course != null -> ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(course)
            else -> notFound().buildAndAwait()
        }
    }

    suspend fun save(request: ServerRequest): ServerResponse {
        val course = request.awaitBody<Course>()
        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(courseRepository.insert(course))
    }

    suspend fun findAll(request: ServerRequest) = ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(courseRepository.findAll())
}