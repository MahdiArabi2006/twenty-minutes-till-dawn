package io.github.some_example_name.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.User;
import io.github.some_example_name.save.UserRepository;
import io.github.some_example_name.view.FirstMenu;
import io.github.some_example_name.view.ProfileMenu;

public class ProfileMenuController {
    private final ProfileMenu view;
    private final UserRepository userRepository = new UserRepository();

    public ProfileMenuController(ProfileMenu view) {
        this.view = view;
    }

    public void changePassword(String password) {
        if (!isPasswordStrong(password)) {
            view.getChangePasswordDialog().hide();
            view.showError("use strong password");
        } else {
            App.getLoggedInUser().setPassword(password);
            userRepository.updateUser(App.getLoggedInUser());
            view.getChangePasswordDialog().hide();
        }
    }

    public void changeUsername(String username) {
        if (!isUsernameUnique(username)) {
            view.getChangeUsernameDialog().hide();
            view.showError("this username already taken");
        } else {
            App.getLoggedInUser().setUsername(username);
            userRepository.updateUser(App.getLoggedInUser());
            view.getChangeUsernameDialog().hide();
        }
    }

    public void deleteAccount() {
        userRepository.deleteUser(App.getLoggedInUser().getUsername());
        App.setLoggedInUser(null);
        view.getDeleteAccountDialog().hide();
        Main.getInstance().setScreen(new FirstMenu());
    }

    public void changeAvatar(Image avatar) {
        App.getLoggedInUser().setAvatar(avatar);
        userRepository.updateUser(App.getLoggedInUser());
    }

    private boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[()*&^%$#@]).{8,}$");
    }

    private boolean isUsernameUnique(String username) {
        User user = userRepository.getUser(username);
        return user == null;
    }
}
