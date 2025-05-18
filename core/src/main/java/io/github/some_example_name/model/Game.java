package io.github.some_example_name.model;

import io.github.some_example_name.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private final GameTimer gameTimer;
    private final Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Seed> seeds = new ArrayList<>();
    private boolean autoAimEnable;
    private boolean isElderSpawn;
    private float lastDashedElder = 5f;
    private float lastAutoShoot;
    private boolean playAsGuest;
    private float lastSpawnTentacle = 0;
    private float lastSpawnEyeBat = 0;

    public Game(GameTimer gameTimer, Player player) {
        this.gameTimer = gameTimer;
        this.player = player;
        generateRandomTree();
    }

    public Player getPlayer() {
        return player;
    }

    public GameTimer getGameTimer() {
        return gameTimer;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public float getLastSpawnTentacle() {
        return lastSpawnTentacle;
    }

    public void setLastSpawnTentacle(float lastSpawnTentacle) {
        this.lastSpawnTentacle = lastSpawnTentacle;
    }

    public float getLastSpawnEyeBat() {
        return lastSpawnEyeBat;
    }

    public void setLastSpawnEyeBat(float lastSpawnEyeBat) {
        this.lastSpawnEyeBat = lastSpawnEyeBat;
    }

    private void generateRandomTree() {
        Random random = new Random();
        int count = random.nextInt(10, 15);
        int placed = 0;
        int maxAttempts = 1000;
        int attempts = 0;

        while (placed < count && attempts < maxAttempts) {
            int viewportWidth = (int) Main.getInstance().getCamera().viewportWidth;
            int viewportHeight = (int) Main.getInstance().getCamera().viewportHeight;
            int randX = random.nextInt(0, viewportWidth);
            int randY = random.nextInt(0, viewportHeight);

            if (isTreeCanBeInPosition(randX, randY)) {
                Tree tree = new Tree(randX, randY);
                enemies.add(tree);
                placed++;
            }

            attempts++;
        }
    }

    private boolean isTreeCanBeInPosition(int x, int y) {
        float treeWidth = GameAsset.treeTexture.getWidth();
        float treeHeight = GameAsset.treeTexture.getHeight();

        for (Enemy enemy : enemies) {
            if (x < enemy.getX() + enemy.getWidth() &&
                x + treeWidth > enemy.getX() &&
                y < enemy.getY() + enemy.getHeight() &&
                y + treeHeight > enemy.getY()) {
                return false;
            }
        }
        return true;
    }

    public List<Seed> getSeeds() {
        return seeds;
    }

    public boolean isPlayAsGuest() {
        return playAsGuest;
    }

    public void setPlayAsGuest(boolean playAsGuest) {
        this.playAsGuest = playAsGuest;
    }

    public boolean isAutoAimEnable() {
        return autoAimEnable;
    }

    public void setAutoAimEnable(boolean autoAimEnable) {
        this.autoAimEnable = autoAimEnable;
    }

    public float getLastAutoShoot() {
        return lastAutoShoot;
    }

    public void setLastAutoShoot(float lastAutoShoot) {
        this.lastAutoShoot = lastAutoShoot;
    }

    public boolean isElderSpawn() {
        return isElderSpawn;
    }

    public void setElderSpawn(boolean elderSpawn) {
        isElderSpawn = elderSpawn;
    }

    public float getLastDashedElder() {
        return lastDashedElder;
    }

    public void setLastDashedElder(float lastDashedElder) {
        this.lastDashedElder = lastDashedElder;
    }
}
