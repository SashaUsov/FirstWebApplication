package sashaVosu.firstWebApplication.exception;


public class UserExistsException extends RuntimeException{

    private String text;

    public UserExistsException(String s) {
        text = s ;
    }

    public String toString() {
        return  "UserExistsException : " + text;
    }
}
