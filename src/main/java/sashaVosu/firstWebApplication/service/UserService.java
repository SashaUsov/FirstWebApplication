package sashaVosu.firstWebApplication.service;

import org.springframework.stereotype.Service;
import sashaVosu.firstWebApplication.converters.UserConverters;
import sashaVosu.firstWebApplication.domain.User;
import sashaVosu.firstWebApplication.domain.dto.CreateUserModel;
import sashaVosu.firstWebApplication.domain.dto.UserModel;
import sashaVosu.firstWebApplication.exception.UserExistsException;
import sashaVosu.firstWebApplication.repo.UserRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserModel> getUserList() {
        return userRepo.findAll().stream()
                .map(UserConverters::toModel).collect(Collectors.toList());
    }

    public UserModel userCreate(CreateUserModel model) {

        User userFromDb = userRepo.findOneByNickNameAndEmail(model.getNickName(), model.getEmail());

        if (userFromDb == null) {

            User newUser = UserConverters.toEntity(model);

            return UserConverters.toModel(userRepo.save(newUser));

        } else {

            throw new UserExistsException("User with this nickname or email already exists");
        }
    }


    }

