package com.rainbow.services

import org.springframework.web.reactive.function.server.coRouter

fun routes(userHandler: UserHandler,
           courseHandler: CourseHandler,
           subscriptionHandler: SubscriptionHandler) = coRouter {
    GET("/api/user/{id}", userHandler::findOne)
    GET("/api/user", userHandler::findAll)
    POST("/api/user", userHandler::create)

    GET("/api/course/{id}", courseHandler::findOne)
    GET("/api/course", courseHandler::findAll)
    POST("/api/course", courseHandler::create)
    POST("/api/subscription", subscriptionHandler::subscribe)
}