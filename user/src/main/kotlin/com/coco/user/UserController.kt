package com.coco.user

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserController {

    @GetMapping("/user-service")
    fun index(): String {
        return "userservice"
    }
}