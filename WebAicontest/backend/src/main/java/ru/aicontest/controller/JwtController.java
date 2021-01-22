package ru.aicontest.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.aicontest.exception.ValidationException;
import ru.aicontest.form.login.UserCredentials;
import ru.aicontest.form.login.UserRegisterCredentials;
import ru.aicontest.form.login.validator.UserCredentialsEnterValidator;
import ru.aicontest.service.JwtService;
import ru.aicontest.service.UserService;
import ru.aicontest.util.BindingResultUtils;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/1")
public class JwtController extends ApiController {
    private final JwtService jwtService;
    private final UserCredentialsEnterValidator userCredentialsEnterValidator;
    private final UserService userService;

    public JwtController(JwtService jwtService, UserCredentialsEnterValidator userCredentialsEnterValidator, UserService userService) {
        this.jwtService = jwtService;
        this.userCredentialsEnterValidator = userCredentialsEnterValidator;
        this.userService = userService;
    }

    @InitBinder("userCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsEnterValidator);
    }

    @PostMapping("jwt")
    public String createJwt(@RequestBody @Valid UserCredentials userCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        return jwtService.create(userCredentials.getLogin(), userCredentials.getPassword());
    }


    @PostMapping("register")
    public void register(@RequestBody @Valid UserRegisterCredentials userRegisterCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        userService.register(userRegisterCredentials);
    }
}
