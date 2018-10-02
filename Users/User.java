package Users;

public class User {
    
    //private final String name;
    private final String login;
    //private final String password;
    private final String role;
    
    public User (String login, String role) {
        //this.name = name;
        this.login = login;
        //this.password = password;
        this.role = role;
    }   

    public String getRole() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", role=" + role + '}';
    }
    
}