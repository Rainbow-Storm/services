package com.rainbow.services

import org.springframework.web.reactive.function.server.coRouter

fun routes(userHandler: UserHandler, courseHandler: CourseHandler) = coRouter {
    GET("/api/user/{id}", userHandler::findOne)
    GET("/api/user", userHandler::findAll)
    POST("/api/user", userHandler::save)

    GET("/api/course/{id}", courseHandler::findOne)
    GET("/api/course", courseHandler::findAll)
    POST("/api/course", courseHandler::save)
}