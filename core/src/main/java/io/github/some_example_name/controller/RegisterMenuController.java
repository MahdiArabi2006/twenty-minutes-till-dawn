package io.github.some_example_name.controller;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;
import io.github.some_example_name.save.UserRepository;
import io.github.some_example_name.view.*;

import java.util.Random;

public class RegisterMenuController {
    private final RegisterMenu view;
    private final UserRepository userRepository = new UserRepository();

    public RegisterMenuController(RegisterMenu menu) {
        this.view = menu;
    }

    public void register(String username, String password, String answer) {
        if (username.isEmpty() || password.isEmpty()) {
            view.showError(LanguageManager.get(TextKey.MENU_REGISTER_ERROR1));
        } else if (!isUsernameUnique(username)) {
            view.showError(LanguageManager.get(TextKey.MENU_REGISTER_ERROR2));
        } else if (!isPasswordStrong(password)) {
            view.showError(LanguageManager.get(TextKey.MENU_REGISTER_ERROR3));
        } else {
            Image image = getRandomAvatar();
            User user = new User(username.trim(), password.trim(), answer.trim(), image);
            userRepository.saveUser(user);
            Main.getInstance().setScreen(new FirstMenu());
        }
    }

    public void startGameAsGuest() {
        Main.getInstance().setScreen(new PreGameMenu(true));
    }

    public Menu getView() {
        return view;
    }

    private boolean isUsernameUnique(String username) {
        User user = userRepository.getUser(username);
        return user==null;
    }

    private boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[()*&^%$#@]).{8,}$");
    }

    private Image getRandomAvatar() {
        Random random = new Random();
        int index = random.nextInt(Math.abs(random.nextInt(1, 100)) % GameAsset.getAvatars().size());
        return GameAsset.getAvatars().get(index);
    }
}
