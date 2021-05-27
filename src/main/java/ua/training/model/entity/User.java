package ua.training.model.entity;

public class User {

    private long id;
    private String login;
    private String password_hash;
    private Role role;
    private boolean isBlocked;

    public User(String login, String password_hash, Role role, boolean isBlocked) {
        this.login = login;
        this.password_hash = password_hash;
        this.role = role;
        this.isBlocked = isBlocked;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}
