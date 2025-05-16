package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.Ability;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.Seed;
import io.github.some_example_name.view.PauseMenu;

import java.util.Random;

public class PlayerController {
    public void update() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        player.getPlayerSprite().setPosition(player.getX(), player.getY());
        player.getPlayerSprite().draw(Main.getInstance().getBatch());

        idleAnimation();

        handlePlayerInput();
        handlePlayerCollision();
        handlePlayerAbility();
    }


    public void handlePlayerInput() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setY(player.getY() + player.getSpeed());
            player.getPlayerCollisionRectangle().move(player.getX(), player.getY());
            walkAnimation();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setX(player.getX() + player.getSpeed());
            player.getPlayerCollisionRectangle().move(player.getX(), player.getY());
            walkAnimation();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setY(player.getY() - player.getSpeed());
            player.getPlayerCollisionRectangle().move(player.getX(), player.getY());
            walkAnimation();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setX(player.getX() - player.getSpeed());
            player.getPlayerCollisionRectangle().move(player.getX(), player.getY());
            walkAnimation();
            player.getPlayerSprite().flip(true, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)){
            player.setLastReloadWeapon(App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime());
            player.resetAmmo();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.P)){
            reduceTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)){
            increaseLevel();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.O)){
            increaseHealth();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            Main.getInstance().setScreen(new PauseMenu());
        }
    }

    private void reduceTime(){
        if (App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime() >= 60){
            App.getLoggedInUser().getLastGame().getGameTimer().setRemainingTime(App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime() - 60);
        }
    }

    private void increaseLevel(){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        player.setXP(player.getXP() + 20);
    }

    private void increaseHealth(){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (player.getHealth() < player.getMaxHP()){
            player.setHealth(player.getMaxHP());
        }
    }

    private void handlePlayerAbility(){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (player.getAbility() != null){
            if (player.getAbility().equals(Ability.VITALITY)){
                player.setMaxHP(player.getCharacter().getHP() + 1);
            }
            if (player.getAbility().equals(Ability.PROJECTILE_INCREASE)){
                player.setProjectile(player.getWeapon().getWeaponType().getProjectile() + 1);
            }
            if (player.getAbility().equals(Ability.AMMO_INCREASE)){
                player.setMaxAmmo(player.getWeapon().getWeaponType().getAmmoMax() + 5);
            }
            if (player.getAbility().equals(Ability.SPEEDY)){
                player.setTimeAbilityBegin(player.getTimeAbilityBegin() + Gdx.graphics.getDeltaTime());
                if (player.getTimeAbilityBegin() <= 10){
                    player.setSpeed(player.getCharacter().getSpeed() * 2);
                }
                else {
                    player.setSpeed(player.getCharacter().getSpeed());
                    player.changeAbility(null);
                }
            }
            if (player.getAbility().equals(Ability.DAMAGER)){
                player.setTimeAbilityBegin(player.getTimeAbilityBegin() + Gdx.graphics.getDeltaTime());
                if (player.getTimeAbilityBegin() <= 10){
                    player.setDamage((int) (player.getWeapon().getWeaponType().getDamage() * 1.25));
                }
                else {
                    player.setDamage(player.getWeapon().getWeaponType().getDamage());
                    player.changeAbility(null);
                }
            }
        }
    }

    public static void handleUpdateLevel(){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        Random random = new Random();
        player.changeAbility(Ability.values()[random.nextInt(0,4)]);
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

    private void walkAnimation(){
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

    private void handlePlayerCollision(){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        for (Seed seed : App.getLoggedInUser().getLastGame().getSeeds()) {
            if (seed.getCollisionRectangle().collidesWith(player.getPlayerCollisionRectangle())){
                player.setXP(player.getXP() + 3);
                seed.setPicked(true);
            }
        }
        App.getLoggedInUser().getLastGame().getSeeds().removeIf(Seed::isPicked);
    }
}
