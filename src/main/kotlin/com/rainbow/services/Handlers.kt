package com.rainbow.services

import CourseRepository
import UserRepository
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*

class UserHandler(private val userRepository: UserRepository) {
    suspend fun findOne(request: ServerRequest): ServerResponse {
        val user = userRepository.queryById(request.pathVariable("id"))
        return when {
            user != null -> ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(user)
            else -> notFound().buildAndAwait()
        }
    }

    suspend fun findAll(request: ServerRequest): ServerResponse {
        return ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(userRepository.queryAll())
    }

    suspend fun create(request: ServerRequest):ServerResponse {
        val body = request.awaitBody<User>()

        print(body)
        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(userRepository.insert(body))
    }

}

class CourseHandler(private val courseRepository: CourseRepository) {
    suspend fun findOne(request: ServerRequest): ServerResponse {
        val course = courseRepository.queryById(request.pathVariable("id"))
        return when {
            course != null -> ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(course)
            else -> notFound().buildAndAwait()
        }
    }

    suspend fun create(request: ServerRequest): ServerResponse {
        val course = request.awaitBody<Course>()
        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(courseRepository.insert(course))
    }

    suspend fun findAll(request: ServerRequest) = ok().contentType(MediaType.APPLICATION_JSON).bodyAndAwait(courseRepository.queryAll())
}

class SubscriptionHandler(private val courseRepository: CourseRepository) {
    suspend fun subscribe(request: ServerRequest): ServerResponse {
        val courseId = request.queryParam("courseId").get()
        val course = courseRepository.queryById(courseId)
        if (course != null) {
            val subscriberId = request.queryParam("userId").get()
            if (course.subscription == null) {
                course.subscription = Subscription(mutableListOf(subscriberId))
            } else  {
                if (subscriberId in course.subscription!!.subscribers) {
                    return badRequest().buildAndAwait()
                }
                course.subscription!!.subscribers.add(subscriberId)
            }
            return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(courseRepository.update(course))
        } else {
            return  notFound().buildAndAwait()
        }
    }
}