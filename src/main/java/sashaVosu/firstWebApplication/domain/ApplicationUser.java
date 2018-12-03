package sashaVosu.firstWebApplication.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "account")
public class ApplicationUser {

    @Column(name = "id", updatable = false, nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usr_sequence")
    private Long id;

    @Column(name = "nick_name")
    @NotNull
    @NotBlank(message = "Nickname cannot be empty")
    private String nickName;

    @Column(name = "email")
    @NotNull
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email is not correct")
    private String email;

    @Column(name = "password")
    @NotNull
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private String age;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "registration_date", updatable = false, nullable = false)
    private LocalDateTime registration;

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "channel_id")},
            inverseJoinColumns = {@JoinColumn(name = "subscriber_id")}
    )
    private Set<ApplicationUser> subscribers = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "channel_id")}
    )
    private Set<ApplicationUser> subscriptions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tweet_mark_user",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tweet_id")}
    )
    private Set<Tweet> userMarkedTweetList = new HashSet<>();

    @Column(name = "is_active")
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
}
