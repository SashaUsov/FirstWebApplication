package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;
import lombok.Setter;
import sashaVosu.firstWebApplication.domain.User;

import java.util.HashSet;
import java.util.Set;

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

    private Integer subscribersCount;

    private Integer subscriptionsCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel model = (UserModel) o;

        if (!id.equals(model.id)) return false;
        if (!nickName.equals(model.nickName)) return false;
        if (firstName != null ? !firstName.equals(model.firstName) : model.firstName != null) return false;
        if (lastName != null ? !lastName.equals(model.lastName) : model.lastName != null) return false;
        if (gender != null ? !gender.equals(model.gender) : model.gender != null) return false;
        if (age != null ? !age.equals(model.age) : model.age != null) return false;
        if (!email.equals(model.email)) return false;
        if (subscribersCount != null ? !subscribersCount.equals(model.subscribersCount) : model.subscribersCount != null)
            return false;
        return subscriptionsCount != null ? subscriptionsCount.equals(model.subscriptionsCount) : model.subscriptionsCount == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nickName.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + (subscribersCount != null ? subscribersCount.hashCode() : 0);
        result = 31 * result + (subscriptionsCount != null ? subscriptionsCount.hashCode() : 0);
        return result;
    }
}
