package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;
import io.github.some_example_name.save.GameSaveManager;
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
        String path = App.getLoggedInUser().getUsername() + ".json";
        GameSaveManager.deleteSaveFile(path);
        Player player = new Player(view.getCharacter(), view.getWeaponType());
        GameTimer gameTimer = new GameTimer(view.getTime() * 60);
        gameTimer.start();
        Game game = new Game(gameTimer, player);
        App.getLoggedInUser().setLastGame(game);
        Main.getInstance().setScreen(new GameView());
    }

    public void startGameAsGuest(){
        User user = new User("guest","1234","asd",GameAsset.getAvatars().get(0));
        App.setLoggedInUser(user);
        Player player = new Player(view.getCharacter(), view.getWeaponType());
        GameTimer gameTimer = new GameTimer(view.getTime() * 60);
        gameTimer.start();
        Game game = new Game(gameTimer, player);
        game.setPlayAsGuest(true);
        App.getLoggedInUser().setLastGame(game);
        Main.getInstance().setScreen(new GameView());
    }
}
