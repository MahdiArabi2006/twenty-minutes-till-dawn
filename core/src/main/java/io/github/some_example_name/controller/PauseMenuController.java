package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.view.MainMenu;
import io.github.some_example_name.view.PauseMenu;

public class PauseMenuController {
    private final PauseMenu view;

    public PauseMenuController(PauseMenu view){
        this.view = view;
    }

    public void giveUp(){
        App.getLoggedInUser().setLastGame(null);
        Main.getInstance().setScreen(new MainMenu());
    }
}
