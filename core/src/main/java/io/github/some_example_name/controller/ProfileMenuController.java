package io.github.some_example_name.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.User;
import io.github.some_example_name.view.FirstMenu;
import io.github.some_example_name.view.ProfileMenu;

public class ProfileMenuController {
    private final ProfileMenu view;

    public ProfileMenuController(ProfileMenu view) {
        this.view = view;
    }

    public void changePassword(String password) {
        if (!isPasswordStrong(password)) {
            view.getChangePasswordDialog().hide();
            view.showError("use strong password");
        } else {
            App.getLoggedInUser().setPassword(password);
            view.getChangePasswordDialog().hide();
        }
    }

    public void changeUsername(String username) {
        if (!isUsernameUnique(username)) {
            view.getChangeUsernameDialog().hide();
            view.showError("this username already taken");
        } else {
            App.getLoggedInUser().setUsername(username);
            view.getChangeUsernameDialog().hide();
        }
    }

    public void deleteAccount() {
        App.getUsers().remove(App.getLoggedInUser());
        App.setLoggedInUser(null);
        view.getDeleteAccountDialog().hide();
        Main.getInstance().setScreen(new FirstMenu());
    }

    public void changeAvatar(Image avatar) {
        App.getLoggedInUser().setAvatar(avatar);
    }

    private boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[()*&^%$#@]).{8,}$");
    }

    private boolean isUsernameUnique(String username) {
        for (User user : App.getUsers()) {
            if (username.equals(user.getUsername())) {
                return false;
            }
        }
        return true;
    }
}
