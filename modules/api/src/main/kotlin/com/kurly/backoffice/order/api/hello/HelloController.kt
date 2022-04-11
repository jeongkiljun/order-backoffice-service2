package com.kurly.backoffice.order.api.hello

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/api/v1/hello")
    fun hello(): String {
        return "hello bro"
    }
}
