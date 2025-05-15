package io.github.some_example_name.model;

public class Game {
    private final int totalTime;
    private final Player player;

    public Game(int totalTime, Player player) {
        this.totalTime = totalTime;
        this.player = player;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public Player getPlayer() {
        return player;
    }
}
