package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.save.GameSaveManager;
import io.github.some_example_name.save.UserRepository;
import io.github.some_example_name.view.FirstMenu;
import io.github.some_example_name.view.MainMenu;

public class AfterGameController {
    private final UserRepository userRepository = new UserRepository();

    public void afterGme(int score, int survivalTime, int kill) {
        App.getLoggedInUser().setScore(score + App.getLoggedInUser().getScore());
        App.getLoggedInUser().setMostSurvivalTime(Math.max(survivalTime, App.getLoggedInUser().getMostSurvivalTime()));
        App.getLoggedInUser().setKillNumber(kill + App.getLoggedInUser().getKillNumber());
        if (App.getLoggedInUser().getLastGame().isPlayAsGuest()) {
            App.getLoggedInUser().setLastGame(null);
            App.setLoggedInUser(null);
            Main.getInstance().setScreen(new FirstMenu());
        } else {
            String path = App.getLoggedInUser().getUsername() + ".json";
            GameSaveManager.deleteSaveFile(path);
            App.getLoggedInUser().setLastGame(null);
            userRepository.updateUser(App.getLoggedInUser());
            Main.getInstance().setScreen(new MainMenu());
        }
    }
}
