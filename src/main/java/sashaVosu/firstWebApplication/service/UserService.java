package sashaVosu.firstWebApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.controller.UserController;
import sashaVosu.firstWebApplication.domain.User;

@Service
public class UserService {

    @Autowired
    private UserController userController;

    public boolean getUser(String nickName) {

        for (User user : userController.getUserList()){

            if (nickName.equals(user.getNickName())) return true;
        }

        return false;
    }
}
