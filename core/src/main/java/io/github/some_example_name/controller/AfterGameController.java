package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.view.FirstMenu;
import io.github.some_example_name.view.MainMenu;

public class AfterGameController {
    public void afterGme(int score){
        App.getLoggedInUser().setScore(score + App.getLoggedInUser().getScore());
        if (App.getLoggedInUser().getLastGame().isPlayAsGuest()){
            App.getLoggedInUser().setLastGame(null);
            App.setLoggedInUser(null);
            Main.getInstance().setScreen(new FirstMenu());
        }
        else {
            App.getLoggedInUser().setLastGame(null);
            Main.getInstance().setScreen(new MainMenu());
        }
    }
}
