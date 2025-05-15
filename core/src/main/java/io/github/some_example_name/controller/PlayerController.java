package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.Player;

public class PlayerController {

    public PlayerController() {
    }

    public void update() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        player.getPlayerSprite().draw(Main.getInstance().getBatch());

        if (player.isPlayerIdle()) {
            idleAnimation();
        }

        handlePlayerInput();
    }


    public void handlePlayerInput() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setY(player.getY() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setX(player.getX() - player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setY(player.getY() + player.getSpeed());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setX(player.getX() + player.getSpeed());
            player.getPlayerSprite().flip(true, false);
        }
    }


    public void idleAnimation() {
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
}
