package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Game;
import io.github.some_example_name.save.GameSaveManager;
import io.github.some_example_name.view.FirstMenu;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.view.MainMenu;

public class MainMenuController {
    private final MainMenu view;

    public MainMenuController(MainMenu view) {
        this.view = view;
    }

    public void logout() {
        App.setLoggedInUser(null);
        Main.getInstance().setScreen(new FirstMenu());
    }

    public void startSavedGame(){
        String path = App.getLoggedInUser().getUsername() + ".json";
        Game game = GameSaveManager.loadGame(path);
        if (game != null){
            game.getGameTimer().start();
            App.getLoggedInUser().setLastGame(game);
            Main.getInstance().setScreen(new GameView());
        }
    }
}
