package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Game;
import io.github.some_example_name.model.GameTimer;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.view.PreGameMenu;

public class PreGameController {
    private final PreGameMenu view;

    public PreGameController(PreGameMenu view) {
        this.view = view;
    }

    public void startGame() {
        if (view.getCharacter()==null) {
            view.showError("choose your character first");
            return;
        }
        if (view.getWeaponType()==null) {
            view.showError("choose your weapon first");
            return;
        }
        if (view.getTime()==null) {
            view.showError("choose your time first");
            return;
        }
        Player player = new Player(App.getLoggedInUser(), view.getCharacter(), view.getWeaponType());
        GameTimer gameTimer = new GameTimer(view.getTime() * 60)  ;
        gameTimer.start();
        Game game = new Game(gameTimer, player);
        App.getLoggedInUser().setLastGame(game);
        Main.getInstance().setScreen(new GameView());
    }
}
