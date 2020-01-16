package com.rainbow.services

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import reactor.core.publisher.Operators

@Document
data class User(
        @Id val id: String?,
        val phone: String,
        val nickname: String,
        val avatar: String?
)

@Document
data class Course(
        @Id val id: String?,
        val title: String,
        val description: String,
        val createBy: String,
        val questions: List<String>,
        var subscription: Subscription?
)

class Subscription(
    val subscribers: MutableList<String> = mutableListOf()
)
