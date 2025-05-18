package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.save.GameSaveManager;
import io.github.some_example_name.view.AfterGameView;
import io.github.some_example_name.view.MainMenu;
import io.github.some_example_name.view.PauseMenu;

public class PauseMenuController {
    private final PauseMenu view;

    public PauseMenuController(PauseMenu view){
        this.view = view;
    }

    public void giveUp(){
        Main.getInstance().setScreen(new AfterGameView(false));
    }

    public void saveAndQuit(){
        App.getLoggedInUser().getLastGame().getGameTimer().pause();
        String path = App.getLoggedInUser().getUsername() + ".json";
        GameSaveManager.saveGame(App.getLoggedInUser().getLastGame(),path);
        App.getLoggedInUser().setLastGame(null);
        Main.getInstance().setScreen(new MainMenu());
    }
}
