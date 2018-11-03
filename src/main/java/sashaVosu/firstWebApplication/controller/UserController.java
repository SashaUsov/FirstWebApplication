package sashaVosu.firstWebApplication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.exception.NotFoundException;
import sashaVosu.firstWebApplication.exception.UserExistsException;
import sashaVosu.firstWebApplication.pojo.PojoUser;
import sashaVosu.firstWebApplication.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    private List<User> userList = new ArrayList<User>() {{
        User firstUser = new User();
        firstUser.setNickName("firstUser");
        firstUser.setEmail("sashavosu@gmail.com");
        firstUser.setFirstName("Sasha");
        firstUser.setLastName("Vosu");
        firstUser.setGender("male");
        firstUser.setAge("28");
        add(firstUser);
    }};

    @GetMapping
    private List<User> listOfUser() throws NotFoundException{

        return userList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }


    @PostMapping
    public String createUser(@RequestBody PojoUser pojoUser) throws UserExistsException{

        if (!userService.getUser(pojoUser.getNickName())) {

            if (pojoUser.getNickName().length() >= 3 && pojoUser.getNickName().length() <= 10) {

                User user = new User();
                user.setNickName(pojoUser.getNickName());
                user.setFirstName(pojoUser.getFirstName());
                user.setLastName(pojoUser.getLastName());

                if (!pojoUser.getEmail().isEmpty() && !pojoUser.getEmail().equals(null)) {
                    user.setEmail(pojoUser.getEmail());

                } else {
                    return "Not valid email";
                }

                user.setGender(pojoUser.getGender());
                user.setAge(pojoUser.getAge());
                userList.add(user);

                return user.getNickName();

            } else {

                return "Bad credentials";
            }
        }

        throw new UserExistsException("User with this nickname already exists");
    }
}
