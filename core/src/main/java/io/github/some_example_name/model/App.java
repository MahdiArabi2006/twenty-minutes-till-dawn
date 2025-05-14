package io.github.some_example_name.model;

import java.util.ArrayList;
import java.util.List;

public class App {
    private static App instance;
    private static final List<User> users = new ArrayList<>();
    private static User loggedInUser = null;

    private App() {
    }

    public static App getInstance() {
        if (instance==null) instance = new App();
        return instance;
    }

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
