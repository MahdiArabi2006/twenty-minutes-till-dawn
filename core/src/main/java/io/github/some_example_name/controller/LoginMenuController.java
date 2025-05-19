package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.LanguageManager;
import io.github.some_example_name.model.TextKey;
import io.github.some_example_name.model.User;
import io.github.some_example_name.save.UserRepository;
import io.github.some_example_name.view.LoginMenu;
import io.github.some_example_name.view.MainMenu;

public class LoginMenuController {
    private final LoginMenu view;
    private final UserRepository userRepository = new UserRepository();

    public LoginMenuController(LoginMenu view) {
        this.view = view;
    }

    public LoginMenu getView() {
        return view;
    }

    public void forgetPassword() {
        String username = view.getUsername().getText();
        if (username.isEmpty()) {
            view.showError(LanguageManager.get(TextKey.MENU_FORGETPASS_ERROR1));
            return;
        }
        User user = getUser(username.trim());
        if (user==null) {
            view.showError(LanguageManager.get(TextKey.MENU_FORGETPASS_ERROR2));
            return;
        }
        String answer = view.getAnswer().getText();
        if (!user.getAnswerSecurityQuestion().equals(answer)) {
            view.showError(LanguageManager.get(TextKey.MENU_FORGETPASS_ERROR3));
            return;
        }
        if (!isPasswordStrong(view.getNewPassword().getText())) {
            view.showError(LanguageManager.get(TextKey.MENU_FORGETPASS_ERROR4));
            return;
        }
        user.setPassword(view.getNewPassword().getText());
        view.getForgetPasswordDialog().hide();
    }

    public void login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.showError(LanguageManager.get(TextKey.MENU_LOGIN_ERROR1));
            return;
        }
        User user = getUser(username.trim());
        if (user==null) {
            view.showError(LanguageManager.get(TextKey.MENU_LOGIN_ERROR2));
        } else if (!isPasswordCorrect(user, password.trim())) {
            view.showError(LanguageManager.get(TextKey.MENU_LOGIN_ERROR3));
        } else {
            App.setLoggedInUser(user);
            Main.getInstance().setScreen(new MainMenu());
        }
    }

    private User getUser(String username) {
        return userRepository.getUser(username);
    }

    private boolean isPasswordCorrect(User user, String password) {
        return user.getPassword().equals(password);
    }

    private boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[()*&^%$#@]).{8,}$");
    }
}
