package sashaVosu.firstWebApplication.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.Error;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.service.UserService;

import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    //return list of all users
    @GetMapping
    public List<UserModel> listOfUser(Pageable pageable) {

        return userService.getUserList(pageable);
    }

    //create new user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel createUser(@RequestBody CreateUserModel model) {

        return userService.userCreate(model);
    }

    @GetMapping("{id}")
    public UserModel getOneUser(@PathVariable("id") Long id) {

        return userService.getOneUser(id);
    }

    @ExceptionHandler
    public Error handleException(Exception e) {
        e.printStackTrace();
        return new Error(e.getMessage());
    }
}
