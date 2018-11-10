package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;
import lombok.Setter;

//dto class to create user model from entity and give back to frontend
@Getter
@Setter
public class UserModel {

    private Long id;

    private String nickName;

    private String firstName;

    private String lastName;

    private String gender;

    private String age;

    private String email;

}
