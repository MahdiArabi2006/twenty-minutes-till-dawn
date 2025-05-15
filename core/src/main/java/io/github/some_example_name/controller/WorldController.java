package io.github.some_example_name.controller;

import com.badlogic.gdx.graphics.Texture;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Player;

public class WorldController {
    private final Texture backgroundTexture;

    public WorldController() {
        this.backgroundTexture = new Texture("background3.png");
    }

    public void update() {
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        float backgroundX = player.getX();
        float backgroundY = player.getY();
        Main.getInstance().getBatch().draw(backgroundTexture, backgroundX, backgroundY);
    }
}
