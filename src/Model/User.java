package Model;

public class User {
    protected String username;
    protected String password;

    public User(String u, String p) {
        this.username = u;
        this.password = p;
    }

    public boolean login(String u, String p) {
        return username.equals(u) && password.equals(p);
    }
}
