package sashaVosu.firstWebApplication.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.service.UserService;

import java.util.List;

@RestController
@RequestMapping("account")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("list")
    private List<UserModel> listOfUser() {

        return userService.getUserList();
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel createUser(@RequestBody CreateUserModel model) {

        return userService.userCreate(model);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        e.printStackTrace();
        return new Error(e.getMessage());
    }
}
