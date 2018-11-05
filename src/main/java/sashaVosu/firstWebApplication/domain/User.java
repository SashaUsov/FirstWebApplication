package sashaVosu.firstWebApplication.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank(message = "Username cannot be empty")
    private String nickName;

    @NotNull
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not correct")
    private String email;

    @NotNull
    @NotBlank(message = "Password cannot be empty")
    private String password;

    private String firstName;

    private String lastName;

    private String gender;

    private String age;

}
