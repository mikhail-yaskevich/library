package by.it.training.library.bean.impl;

import by.it.training.library.bean.Role;
import by.it.training.library.bean.User;
import by.it.training.library.bean.UserType;

import java.sql.Timestamp;
import java.util.Set;

public class UserBean implements User {

    private int id;
    private String firstName;
    private String lastName;
    private Timestamp beginning;
    private Timestamp ending;
    private Set<Role> roleSet;
    private String login;
    private String password;
    private String email;
    private UserType userType;

    public UserBean() {
    }

    public UserBean(int id, String login, String password, String email, String firstName, String lastName, Timestamp beginning, Timestamp ending, Set<Role> roleSet, UserType userType) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.beginning = beginning;
        this.ending = ending;
        this.roleSet = roleSet;
        this.userType = userType;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public UserType getUserType() {
        return userType;
    }

    @Override
    public Timestamp getBeginning() {
        return beginning;
    }

    @Override
    public Timestamp getEnding() {
        return ending;
    }

    @Override
    public boolean allowed(Role role) {
        return roleSet.contains(role);
    }
}
