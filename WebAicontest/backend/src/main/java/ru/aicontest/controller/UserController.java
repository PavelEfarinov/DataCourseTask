package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.domain.BaseUserEntity;

@RestController
@RequestMapping("/api/1")
public class UserController extends ApiController {
    @GetMapping("users/authorized")
    public BaseUserEntity findAuthorized(BaseUserEntity user)
    {
        return user;
    }
}
