package io.github.some_example_name.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.Ability;
import io.github.some_example_name.model.App;

public class WorldController {
    private final Texture backgroundTexture;
    private final BitmapFont font;

    public WorldController() {
        this.backgroundTexture = new Texture("background3.png");
        this.font = new BitmapFont();
        this.font.getData().setScale(2);
        this.font.setColor(Color.WHITE);
    }

    public void update() {
        Main.getInstance().getBatch().draw(backgroundTexture, 0, 0);
        String time = App.getLoggedInUser().getLastGame().getGameTimer().getFormattedTime();
        String playerLevel = "level: " + App.getLoggedInUser().getLastGame().getPlayer().getLevel();
        font.draw(Main.getInstance().getBatch(), time, 20, Main.getInstance().getCamera().viewportHeight);
        font.draw(Main.getInstance().getBatch(), playerLevel,20,Main.getInstance().getCamera().viewportHeight - 20);
        Ability ability = App.getLoggedInUser().getLastGame().getPlayer().getAbility();
        if (ability != null){
            font.draw(Main.getInstance().getBatch(), ability.getName(),20,Main.getInstance().getCamera().viewportHeight - 40);
        }
    }
}
