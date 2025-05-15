package io.github.some_example_name.model;

import java.util.ArrayList;
import java.util.List;

public class App {
    private static final List<User> users = new ArrayList<>();
    private static User loggedInUser = null;

    public static List<User> getUsers() {
        return users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        App.loggedInUser = loggedInUser;
    }
}
