package sashaVosu.firstWebApplication.domain.dto;

import lombok.Getter;

//dto class to create user model from entity and give back to frontend
@Getter
public class UserModel {

    private Long id;

    private String nickName;

    private String firstName;

    private String lastName;

    private String gender;

    private String age;

    private String email;

    private String fileName;

    private UserModel() {

    }

    public class UserBuilder {

        private UserBuilder() {
            // private constructor
        }

        public UserBuilder setId(Long id) {
            UserModel.this.id = id;

            return this;
        }

        public UserBuilder setNickName(String nickName) {
            UserModel.this.nickName = nickName;

            return this;
        }

        public UserBuilder setLastName(String lastName) {
            UserModel.this.lastName = lastName;

            return this;
        }

        public UserBuilder setGender(String gender) {
            UserModel.this.gender = gender;

            return this;
        }

        public UserBuilder setAge(String age) {
            UserModel.this.age = age;

            return this;
        }

        public UserBuilder setEmail(String email) {
            UserModel.this.email = email;

            return this;
        }

        public UserBuilder setFileName(String fileName) {
            UserModel.this.fileName = fileName;

            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            UserModel.this.firstName = firstName;

            return this;
        }

        public UserModel build() {
            return UserModel.this;
        }

    }

    public static UserBuilder newBuilder() {
        return new UserModel().new UserBuilder();
    }

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
        return fileName != null ? fileName.equals(model.fileName) : model.fileName == null;
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
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        return result;
    }
}
