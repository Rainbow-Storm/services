package com.rainbow.services

import CourseRepository
import UserRepository
import org.springframework.fu.kofu.configuration
import org.springframework.fu.kofu.mongo.reactiveMongodb
import org.springframework.fu.kofu.webflux.webFlux

val dataConfig = configuration {
    beans {
        bean<UserRepository>()
        bean<CourseRepository>()
    }
    reactiveMongodb {
        uri = "mongodb://localhost:27017/foo"
    }
}
val webConfig = configuration {
    beans {
        bean<UserHandler>()
        bean<CourseHandler>()
        bean(::routes)
    }
    webFlux {
        port = if (profiles.contains("test")) 8181 else 8080
        codecs {
            string()
            jackson()
        }
    }
}