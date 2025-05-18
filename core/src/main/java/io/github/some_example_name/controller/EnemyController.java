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
            if (enemy.isDying()) {
                enemy.setDeathTimer(enemy.getDeathTimer() - Gdx.graphics.getDeltaTime());
                float progress = 1f - (enemy.getDeathTimer() / enemy.getDeathDuration());
                float alpha = 1f - progress;
                float scale = 1f + progress * 0.5f;
                float shake = (float) Math.sin(progress * 50) * 3f;
                Main.getInstance().getBatch().setColor(1, 0.4f, 0.4f, alpha);
                enemy.getSprite().setScale(scale);
                enemy.getSprite().setX(enemy.getX() + shake);
                enemy.getSprite().draw(Main.getInstance().getBatch());
                enemy.getSprite().setX(enemy.getX());
                enemy.getSprite().setScale(1f);
                Main.getInstance().getBatch().setColor(1, 1, 1, 1);

                if (enemy.getDeathTimer() <= 0f) {
                    App.getLoggedInUser().getLastGame().getEnemies().remove(enemy);
                    App.getLoggedInUser().getLastGame().getSeeds().add(new Seed(enemy.getSprite().getX(), enemy.getSprite().getY()));
                    if (enemy instanceof EyeBat) {
                        if (App.isEnableSFX()) GameAsset.batDied.play(1f);
                    } else {
                        if (App.isEnableSFX()) GameAsset.monsterDied.play(1f);
                    }
                    break;
                }
            } else {
                enemy.getSprite().draw(Main.getInstance().getBatch());
            }
        }
        for (Seed seed : App.getLoggedInUser().getLastGame().getSeeds()) {
            seed.getSprite().draw(Main.getInstance().getBatch());
        }
        spawnEnemy();
        for (Enemy enemy : App.getLoggedInUser().getLastGame().getEnemies()) {
            if (!(enemy instanceof Tree) && !(enemy instanceof Elder)) {
                moveEnemy(enemy);
            }
            if (enemy instanceof Elder){
                moveElder((Elder)enemy);
            }
            if (enemy instanceof EyeBat) {
                shootEyeBat((EyeBat) enemy);
                updateBullets((EyeBat) enemy);
            }
            handleCollisionEnemyAndPlayer();
        }
    }

    private void handleCollisionEnemyAndPlayer() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (player.isDamaged()) {
            return;
        }
        for (Enemy enemy : App.getLoggedInUser().getLastGame().getEnemies()) {
            if (player.getPlayerCollisionRectangle().collidesWith(enemy.getCollisionRectangle())) {
                player.setHealth(player.getHealth() - 1);
                player.setDamaged(true);
                player.setDamageTimer(3f);
                if (player.getHealth() <= 0) {
                    GameController.handleGameOver(false);
                }
                break;
            }
        }
    }

    private void shootEyeBat(EyeBat eyeBat) {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        eyeBat.setShootTimer(eyeBat.getShootTimer() + Gdx.graphics.getDeltaTime());
        if (eyeBat.getShootTimer() > 3f) {
            Bullet bullet = new Bullet((int) player.getX(), (int) player.getY(), (int) eyeBat.getSprite().getX(), (int) eyeBat.getSprite().getY(), player.getWeapon(), player, true);
            bullet.getSprite().setPosition(eyeBat.getSprite().getX(), eyeBat.getSprite().getY());
            eyeBat.getBullets().add(bullet);
            eyeBat.setShootTimer(0);
        }
    }

    private void moveEnemy(Enemy enemy) {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        Vector2 direction = new Vector2(
            player.getPlayerSprite().getX() - enemy.getSprite().getX(),
            player.getPlayerSprite().getY() - enemy.getSprite().getY()
        ).nor();

        enemy.getSprite().setX(enemy.getSprite().getX() + direction.x);
        enemy.getSprite().setY(enemy.getSprite().getY() + direction.y);
        enemy.setX((int) (enemy.getSprite().getX() + direction.x));
        enemy.setY((int) (enemy.getSprite().getY() + direction.y));
        enemy.getCollisionRectangle().move(enemy.getSprite().getX(), enemy.getSprite().getY());
        walkAnimation(enemy, enemy.getWalk());
    }

    private void moveElder(Elder elder){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();

        if (App.getLoggedInUser().getLastGame().getLastDashedElder() <= 0){
            elder.getSprite().setX(player.getPlayerSprite().getX() + 70);
            elder.getSprite().setY(player.getPlayerSprite().getY());
            elder.setX((int) (player.getPlayerSprite().getX()) + 70);
            elder.setY((int) (player.getPlayerSprite().getY()));
            elder.getCollisionRectangle().move(elder.getSprite().getX(), elder.getSprite().getY());
            walkAnimation(elder, elder.getWalk());
            App.getLoggedInUser().getLastGame().setLastDashedElder(5f);
        }
        else {
            App.getLoggedInUser().getLastGame().setLastDashedElder(App.getLoggedInUser().getLastGame().getLastDashedElder() - Gdx.graphics.getDeltaTime());
        }
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
            b.getCollisionRectangle().move(b.getSprite().getX(), b.getSprite().getY());
            handleCollision(b);
        }
        eyeBat.getBullets().removeIf(Bullet::isDestroyed);
    }

    private void handleCollision(Bullet bullet) {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (player.isDamaged()) {
            return;
        }
        if (bullet.getCollisionRectangle().collidesWith(player.getPlayerCollisionRectangle())) {
            player.setHealth(player.getHealth() - 1);
            player.setDamaged(true);
            player.setDamageTimer(3f);
            bullet.setDestroyed(true);
            if (player.getHealth() <= 0) {
                GameController.handleGameOver(false);
            }
        }
    }

    private void spawnEnemy() {
        spawnTentacle();
        spawnEyeBat();
        spawnElder();
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
                if (App.isEnableSFX()) GameAsset.monsterSpawn.play(1f);
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
                    if (App.isEnableSFX()) GameAsset.monsterSpawn.play(1f);
                    App.getLoggedInUser().getLastGame().setLastSpawnEyeBat(now);
                }
            }
        }
    }

    private void spawnElder() {
        float now = App.getLoggedInUser().getLastGame().getGameTimer().getCountdownDuration() - App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime();
        if (now >= App.getLoggedInUser().getLastGame().getGameTimer().getCountdownDuration() / 2 &&
            !App.getLoggedInUser().getLastGame().isElderSpawn()) {
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
            App.getLoggedInUser().getLastGame().getEnemies().add(new Elder(x,y));
            if (App.isEnableSFX()) GameAsset.monsterSpawn.play(0.5f);
            App.getLoggedInUser().getLastGame().setElderSpawn(true);
        }
    }

    private void walkAnimation(Enemy enemy, Animation<Texture> walk) {
        enemy.getSprite().setRegion(walk.getKeyFrame(enemy.getTime()));

        if (!walk.isAnimationFinished(enemy.getTime())) {
            enemy.setTime(enemy.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            enemy.setTime(0);
        }

        walk.setPlayMode(Animation.PlayMode.LOOP);
    }
}
