package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.User;
import io.github.some_example_name.view.LoginMenu;
import io.github.some_example_name.view.MainMenu;

public class LoginMenuController {
    private final LoginMenu view;

    public LoginMenuController(LoginMenu view) {
        this.view = view;
    }

    public LoginMenu getView() {
        return view;
    }

    public void forgetPassword() {
        String username = view.getUsername().getText();
        if (username.isEmpty()) {
            view.showError("please enter your username");
            return;
        }
        User user = getUser(username.trim());
        if (user==null) {
            view.showError("this username is not exist");
            return;
        }
        String answer = view.getAnswer().getText();
        if (!user.getAnswerSecurityQuestion().equals(answer)) {
            view.showError("your answer is wrong");
            return;
        }
        if (!isPasswordStrong(view.getNewPassword().getText())) {
            view.showError("use a strong password");
            return;
        }
        user.setPassword(view.getNewPassword().getText());
        view.getForgetPasswordDialog().hide();
    }

    public void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.showError("please enter your username and password");
            return;
        }
        User user = getUser(username.trim());
        if (user==null) {
            view.showError("this username is not exist");
        } else if (!isPasswordCorrect(user, password.trim())) {
            view.showError("this password is false");
        } else {
            App.setLoggedInUser(user);
            Main.getInstance().setScreen(MainMenu.getInstance());
        }
    }

    private User getUser(String username) {
        for (User user : App.getUsers()) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;
    }

    private boolean isPasswordCorrect(User user, String password) {
        return user.getPassword().equals(password);
    }

    private boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[()*&^%$#@]).{8,}$");
    }
}
