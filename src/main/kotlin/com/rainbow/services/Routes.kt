package com.rainbow.services

import org.springframework.web.reactive.function.server.coRouter

fun routes(userHandler: UserHandler) = coRouter {
    GET("/api/user/{id}", userHandler::findOne)
    POST("/api/user", userHandler::save)
}