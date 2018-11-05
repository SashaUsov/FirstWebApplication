package sashaVosu.firstWebApplication.domain.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateUserModel {
    @NotNull
    @NotEmpty
    private String nickName;

    private String firstName;

    private String lastName;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    private String gender;

    private String age;
}
