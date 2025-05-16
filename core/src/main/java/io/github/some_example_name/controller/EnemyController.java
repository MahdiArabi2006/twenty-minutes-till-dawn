package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;

import java.util.Random;

public class EnemyController {
    public void update() {
        for (Enemy enemy : App.getLoggedInUser().getLastGame().getEnemies()) {
            enemy.getSprite().draw(Main.getInstance().getBatch());
        }
        spawnEnemy();
        for (Enemy enemy : App.getLoggedInUser().getLastGame().getEnemies()) {
            if (!(enemy instanceof Tree)){
                moveEnemy(enemy);
            }
            if (enemy instanceof EyeBat){
                shootEyeBat((EyeBat) enemy);
                updateBullets((EyeBat) enemy);
            }
        }
    }

    private void shootEyeBat(EyeBat eyeBat){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        eyeBat.setShootTimer(eyeBat.getShootTimer() + Gdx.graphics.getDeltaTime());
        if (eyeBat.getShootTimer() > 3f){
            Bullet bullet = new Bullet((int) player.getX(), (int) player.getY(), (int) eyeBat.getSprite().getX(), (int) eyeBat.getSprite().getY(),player.getWeapon());
            bullet.getSprite().setPosition(eyeBat.getSprite().getX(),eyeBat.getSprite().getY());
            eyeBat.getBullets().add(bullet);
            eyeBat.setShootTimer(0);
        }
    }

    private void moveEnemy(Enemy enemy){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        Vector2 direction = new Vector2(
            player.getPlayerSprite().getX() - enemy.getSprite().getX(),
            player.getPlayerSprite().getY() - enemy.getSprite().getY()
        ).nor();

        enemy.getSprite().setX(enemy.getSprite().getX() + direction.x);
        enemy.getSprite().setY(enemy.getSprite().getY() + direction.y);
        walkAnimation(enemy,enemy.getWalk());
    }

    private void updateBullets(EyeBat eyeBat) {
        for (Bullet b : eyeBat.getBullets()) {
            b.getSprite().draw(Main.getInstance().getBatch());
            Vector2 direction = new Vector2(
                b.getX() - b.getX_weapon(),
                b.getY() - b.getY_weapon()
            ).nor();

            b.getSprite().setX(b.getSprite().getX() + direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
        }
    }

    private void spawnEnemy() {
        spawnTentacle();
        spawnEyeBat();
    }

    private void spawnTentacle() {
        float now = App.getLoggedInUser().getLastGame().getGameTimer().getCountdownDuration() - App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime();
        if (now - App.getLoggedInUser().getLastGame().getLastSpawnTentacle() > 3) {
            int numberOfTenacle = (int) (now / 30);
            for (int i = 0; i < numberOfTenacle; i++) {
                Random random = new Random();
                int side = random.nextInt(4);
                float camX = Main.getInstance().getCamera().position.x;
                float camY = Main.getInstance().getCamera().position.y;
                float viewportWidth = Main.getInstance().getCamera().viewportWidth;
                float viewportHeight = Main.getInstance().getCamera().viewportHeight;
                int x = 0;
                int y = 0;
                switch (side) {
                    case 0:
                        x = (int) (camX - viewportWidth / 2 + random.nextFloat() * viewportWidth);
                        y = (int) (camY + viewportHeight / 2);
                        break;
                    case 1:
                        x = (int) (camX - viewportWidth / 2 + random.nextFloat() * viewportWidth);
                        y = (int) (camY - viewportHeight / 2);
                        break;
                    case 2:
                        x = (int) (camX - viewportWidth / 2);
                        y = (int) (camY - viewportHeight / 2 + random.nextFloat() * viewportHeight);
                        break;
                    case 3:
                        x = (int) (camX + viewportWidth / 2);
                        y = (int) (camY - viewportHeight / 2 + random.nextFloat() * viewportHeight);
                        break;
                }
                App.getLoggedInUser().getLastGame().getEnemies().add(new Tentacle(x, y));
                App.getLoggedInUser().getLastGame().setLastSpawnTentacle(now);
            }
        }
    }

    private void spawnEyeBat() {
        float now = App.getLoggedInUser().getLastGame().getGameTimer().getCountdownDuration() - App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime();
        float totalTime = App.getLoggedInUser().getLastGame().getGameTimer().getCountdownDuration();
        if (now >= totalTime / 4) {
            if (now - App.getLoggedInUser().getLastGame().getLastSpawnEyeBat() > 10) {
                int numberOfEyeBat = (int) ((4 * now - totalTime + 30) / 30);
                for (int i = 0; i < numberOfEyeBat; i++) {
                    Random random = new Random();
                    int side = random.nextInt(4);
                    float camX = Main.getInstance().getCamera().position.x;
                    float camY = Main.getInstance().getCamera().position.y;
                    float viewportWidth = Main.getInstance().getCamera().viewportWidth;
                    float viewportHeight = Main.getInstance().getCamera().viewportHeight;
                    int x = 0;
                    int y = 0;
                    switch (side) {
                        case 0:
                            x = (int) (camX - viewportWidth / 2 + random.nextFloat() * viewportWidth);
                            y = (int) (camY + viewportHeight / 2);
                            break;
                        case 1:
                            x = (int) (camX - viewportWidth / 2 + random.nextFloat() * viewportWidth);
                            y = (int) (camY - viewportHeight / 2);
                            break;
                        case 2:
                            x = (int) (camX - viewportWidth / 2);
                            y = (int) (camY - viewportHeight / 2 + random.nextFloat() * viewportHeight);
                            break;
                        case 3:
                            x = (int) (camX + viewportWidth / 2);
                            y = (int) (camY - viewportHeight / 2 + random.nextFloat() * viewportHeight);
                            break;
                    }
                    App.getLoggedInUser().getLastGame().getEnemies().add(new EyeBat(x, y));
                    App.getLoggedInUser().getLastGame().setLastSpawnEyeBat(now);
                }
            }
        }
    }

    private void walkAnimation(Enemy enemy,Animation<Texture> walk){
        enemy.getSprite().setRegion(walk.getKeyFrame(enemy.getTime()));

        if (!walk.isAnimationFinished(enemy.getTime())) {
            enemy.setTime(enemy.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            enemy.setTime(0);
        }

        walk.setPlayMode(Animation.PlayMode.LOOP);
    }
}
