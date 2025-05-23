package io.github.some_example_name.controller;

import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameTimer;
import io.github.some_example_name.view.AfterGameView;
import io.github.some_example_name.view.GameView;

public class GameController {
    private final GameView view;
    private final WeaponController weaponController = new WeaponController();
    private final PlayerController playerController = new PlayerController();
    private final WorldController worldController = new WorldController();
    private final EnemyController enemyController = new EnemyController();

    public GameController(GameView view) {
        this.view = view;
    }

    public GameView getView() {
        return view;
    }

    public void updateGame(float delta) {
        if (view!=null) {
            worldController.update();
            playerController.update();
            weaponController.update();
            enemyController.update();
            updateTime(delta);
        }
    }


    public void updateTime(float delta) {
        GameTimer gameTimer = App.getLoggedInUser().getLastGame().getGameTimer();
        if (gameTimer.isRunning() && gameTimer.getRemainingTime() > 0) {
            gameTimer.setRemainingTime(gameTimer.getRemainingTime() - delta);
            if (gameTimer.getRemainingTime() <= 0) {
                gameTimer.setRemainingTime(0);
                gameTimer.setRunning(false);
                handleGameOver(true);
            }
        }
    }

    public static void handleGameOver(boolean win){
        Main.getInstance().setScreen(new AfterGameView(win));
    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public WorldController getWorldController() {
        return worldController;
    }
}
