package com.rainbow.services

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        @Id val id: String?,
        val phone: String,
        val nickname: String,
        val avatar: String?
) {
    constructor(phone: String, nickname: String) : this(null, phone, nickname, null)
}
