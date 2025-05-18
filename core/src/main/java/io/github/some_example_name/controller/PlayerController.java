package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;
import io.github.some_example_name.view.LevelUpView;
import io.github.some_example_name.view.PauseMenu;

import java.util.Random;

public class PlayerController {
    public void update() {
        GameKey gameKey = App.getLoggedInUser().getGameKey();
        boolean isMoving = Gdx.input.isKeyPressed(gameKey.getMOVE_UP()) ||
            Gdx.input.isKeyPressed(gameKey.getMOVE_DOWN()) ||
            Gdx.input.isKeyPressed(gameKey.getMOVE_LEFT()) ||
            Gdx.input.isKeyPressed(gameKey.getMOVE_RIGHT());
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        player.getPlayerSprite().setPosition(player.getX(), player.getY());
        if (player.isDamaged()) {
            player.setDamageTimer(player.getDamageTimer() - Gdx.graphics.getDeltaTime());

            float blink = (float) Math.sin(player.getDamageTimer() * 40);
            float shake = (float) Math.sin(player.getDamageTimer() * 50) * 3f;

            if (blink > 0) {
                Main.getInstance().getBatch().setColor(1, 0.3f, 0.3f, 1);
            } else {
                Main.getInstance().getBatch().setColor(1, 1, 1, 0.3f);
            }

            player.getPlayerSprite().setX(player.getX() + shake);
            player.getPlayerSprite().draw(Main.getInstance().getBatch());
            player.getPlayerSprite().setX(player.getX());
            Main.getInstance().getBatch().setColor(1, 1, 1, 1);

            if (player.getDamageTimer() <= 0) {
                player.setDamaged(false);
                player.setDamageTimer(0f);
            }
        } else {
            if (isMoving) {
                walkAnimation();
            } else {
                idleAnimation();
            }
            LightEffect playerLight = new LightEffect(50f);
            player.getPlayerSprite().draw(Main.getInstance().getBatch());
            playerLight.render(Main.getInstance().getBatch(), player.getX() + player.getPlayerSprite().getWidth() / 2, player.getY() + player.getPlayerSprite().getHeight() / 2);
        }

        Main.getInstance().centerCameraOnPlayer(player.getX(), player.getY());


        handlePlayerInput();
        handlePlayerCollision();
        handlePlayerAbility();
    }

    public void handlePlayerInput() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        GameKey gameKey = App.getLoggedInUser().getGameKey();
        if (Gdx.input.isKeyPressed(gameKey.getMOVE_UP())) {
            player.setY(player.getY() + player.getSpeed());
            player.getPlayerCollisionRectangle().move(player.getX(), player.getY());
        }
        if (Gdx.input.isKeyPressed(gameKey.getMOVE_RIGHT())) {
            player.setX(player.getX() + player.getSpeed());
            player.getPlayerCollisionRectangle().move(player.getX(), player.getY());
        }
        if (Gdx.input.isKeyPressed(gameKey.getMOVE_DOWN())) {
            player.setY(player.getY() - player.getSpeed());
            player.getPlayerCollisionRectangle().move(player.getX(), player.getY());
        }
        if (Gdx.input.isKeyPressed(gameKey.getMOVE_LEFT())) {
            player.setX(player.getX() - player.getSpeed());
            player.getPlayerCollisionRectangle().move(player.getX(), player.getY());
            player.getPlayerSprite().flip(true, false);
        }
        if (Gdx.input.isKeyPressed(gameKey.getRELOAD())) {
            player.setLastReloadWeapon(App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime());
            player.resetAmmo();
            if (App.isEnableSFX()) GameAsset.reloadGun.play(1f);
        }
        if (Gdx.input.isKeyJustPressed((gameKey.getAUTO_AIM()))) {
            App.getLoggedInUser().getLastGame().setAutoAimEnable(!App.getLoggedInUser().getLastGame().isAutoAimEnable());
        }
        if (Gdx.input.isKeyJustPressed(gameKey.getCHEAT_REDUCE_TIME())) {
            reduceTime();
        }
        if (Gdx.input.isKeyJustPressed(gameKey.getCHEAT_INCREASE_LEVEL())) {
            increaseLevel();
        }
        if (Gdx.input.isKeyJustPressed(gameKey.getCHEAT_INCREASE_HEALTH())) {
            increaseHealth();
        }
        if (Gdx.input.isKeyJustPressed(gameKey.getPASS_BOSS())) {
            spawnElder();
        }
        if (Gdx.input.isKeyJustPressed(gameKey.getKILL_ALL())){
            App.getLoggedInUser().getLastGame().getEnemies().clear();
        }
        if (Gdx.input.isKeyPressed(gameKey.getPAUSE())) {
            Main.getInstance().setScreen(new PauseMenu());
        }
    }

    private void reduceTime() {
        if (App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime() >= 60) {
            App.getLoggedInUser().getLastGame().getGameTimer().setRemainingTime(App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime() - 60);
        }
    }

    private void increaseLevel() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        player.setXP(player.getXP() + 20 * player.getLevel());
        player.updateLevel();
    }

    private void increaseHealth() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (player.getHealth() < player.getMaxHP()) {
            player.resetHealth();
        }
    }

    private void spawnElder() {
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
        App.getLoggedInUser().getLastGame().getEnemies().add(new Elder(x, y));
        if (App.isEnableSFX()) GameAsset.monsterSpawn.play(0.5f);
        App.getLoggedInUser().getLastGame().setElderSpawn(true);
    }

    private void handlePlayerAbility() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (player.getAbility()!=null) {
            if (player.getAbility().equals(Ability.VITALITY)) {
                player.setMaxHP(player.getCharacter().getHP() + 1);
            }
            if (player.getAbility().equals(Ability.PROJECTILE_INCREASE)) {
                player.setProjectile(player.getWeapon().getWeaponType().getProjectile() + 1);
            }
            if (player.getAbility().equals(Ability.AMMO_INCREASE)) {
                player.setMaxAmmo(player.getWeapon().getWeaponType().getAmmoMax() + 5);
            }
            if (player.getAbility().equals(Ability.SPEEDY)) {
                player.setTimeAbilityBegin(player.getTimeAbilityBegin() + Gdx.graphics.getDeltaTime());
                if (player.getTimeAbilityBegin() <= 10) {
                    player.setSpeed(player.getCharacter().getSpeed() * 2);
                } else {
                    player.setSpeed(player.getCharacter().getSpeed());
                    player.changeAbility(null);
                }
            }
            if (player.getAbility().equals(Ability.DAMAGER)) {
                player.setTimeAbilityBegin(player.getTimeAbilityBegin() + Gdx.graphics.getDeltaTime());
                if (player.getTimeAbilityBegin() <= 10) {
                    player.setDamage((int) (player.getWeapon().getWeaponType().getDamage() * 1.25));
                } else {
                    player.setDamage(player.getWeapon().getWeaponType().getDamage());
                    player.changeAbility(null);
                }
            }
        }
    }

    public static void handleUpdateLevel() {
        Main.getInstance().setScreen(new LevelUpView());
    }

    private void idleAnimation() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        Animation<Texture> animation = player.getCharacter().getIdle();

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void walkAnimation() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        Animation<Texture> animation = player.getCharacter().getWalk();

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        } else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    private void handlePlayerCollision() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        for (Seed seed : App.getLoggedInUser().getLastGame().getSeeds()) {
            if (seed.getCollisionRectangle().collidesWith(player.getPlayerCollisionRectangle())) {
                player.setXP(player.getXP() + 3);
                player.updateLevel();
                seed.setPicked(true);
            }
        }
        App.getLoggedInUser().getLastGame().getSeeds().removeIf(Seed::isPicked);
    }
}
