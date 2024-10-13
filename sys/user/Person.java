package sys.user;

import sys.exceptions.InvalidLoginException;

public interface Person {
    void login() throws InvalidLoginException;
    void logout();
    void signup();
    void displayMenu();
    boolean isLoggedOut();
}
