package ru.aicontest.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import ru.aicontest.domain.BaseUserEntity;

import javax.servlet.http.HttpServletRequest;

public abstract class ApiController {
    @ModelAttribute
    public BaseUserEntity getUser(HttpServletRequest httpServletRequest) {
        return (BaseUserEntity) httpServletRequest.getAttribute("user");
    }
}
