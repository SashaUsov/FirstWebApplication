package sashaVosu.firstWebApplication.domain.dto;

import lombok.Data;

@Data
public class UserModel {

    private Long id;

    private String nickName;

    private String firstName;

    private String lastName;

    private String gender;

    private String age;
}
