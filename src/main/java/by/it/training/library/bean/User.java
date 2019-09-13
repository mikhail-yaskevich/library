package by.it.training.library.bean;

import java.sql.Timestamp;

public interface User {
    int getId();
    String getLogin();
    String getPassword();
    String getEmail();
    String getFirstName();
    String getLastName();
    UserType getUserType();
    Timestamp getBeginning();
    Timestamp getEnding();
    boolean allowed(Role role);
}
