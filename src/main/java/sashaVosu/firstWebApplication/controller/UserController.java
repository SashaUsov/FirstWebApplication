package sashaVosu.firstWebApplication.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.CreateUserRequest;
import sashaVosu.firstWebApplication.service.UserService;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    private List<User> listOfUser() {

        return userService.getUserList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody CreateUserRequest req) {

        return userService.userCreate(req);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        return new Error(e.getMessage());
    }
}
