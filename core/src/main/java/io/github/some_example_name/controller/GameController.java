package io.github.some_example_name.controller;

import io.github.some_example_name.view.GameView;

public class GameController {
    private final GameView view;
    private final WeaponController weaponController = new WeaponController();
    private final PlayerController playerController = new PlayerController();
    private final WorldController worldController = new WorldController();

    public GameController(GameView view) {
        this.view = view;
    }

    public GameView getView() {
        return view;
    }

    public void updateGame() {
        if (view!=null) {
            worldController.update();
            playerController.update();
            weaponController.update();
        }
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
