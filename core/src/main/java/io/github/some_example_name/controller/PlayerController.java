package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Player;

public class PlayerController {
    public void update() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        player.getPlayerSprite().setPosition(player.getX(), player.getY());
        player.getPlayerSprite().draw(Main.getInstance().getBatch());

        idleAnimation();

        handlePlayerInput();
    }


    public void handlePlayerInput() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setY(player.getY() + player.getSpeed());
            walkAnimation();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setX(player.getX() + player.getSpeed());
            walkAnimation();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setY(player.getY() - player.getSpeed());
            walkAnimation();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setX(player.getX() - player.getSpeed());
            walkAnimation();
            player.getPlayerSprite().flip(true, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)){
            player.setLastReloadWeapon(App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime());
            player.getWeapon().resetAmmo();
        }
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
}
