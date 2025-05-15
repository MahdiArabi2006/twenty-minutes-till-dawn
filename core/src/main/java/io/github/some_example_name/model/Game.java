package io.github.some_example_name.model;

public class Game {
    private final GameTimer gameTimer;
    private final Player player;

    public Game(GameTimer gameTimer, Player player) {
        this.gameTimer = gameTimer;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }
}
