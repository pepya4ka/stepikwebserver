package models;

public class UserProfile {
    private String login;
    private String password;

    public UserProfile(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}