package io.github.some_example_name.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;

public class WorldController {
    private final Texture backgroundTexture;
    private final BitmapFont font;
    private final Texture heart, ammo, kill;
    private final ProgressBar XPBar;

    public WorldController() {
        this.backgroundTexture = new Texture("background3.png");
        this.font = new BitmapFont();
        this.font.getData().setScale(2);
        this.font.setColor(Color.WHITE);
        this.ammo = new Texture("T_AmmoIcon.png");
        this.kill = new Texture("T_CurseFX_0.png");
        this.heart = new Texture("HeartAnimation_0.png");
        this.XPBar = new ProgressBar(0, 100, 1, false, GameAsset.getMenuSkin());
    }

    public void update() {
        OrthographicCamera camera = Main.getInstance().getCamera();
        float offsetX = camera.position.x - camera.viewportWidth / 2;
        float offsetY = camera.position.y - camera.viewportHeight / 2;
        Main.getInstance().getBatch().draw(
            backgroundTexture,
            offsetX,
            offsetY,
            camera.viewportWidth,
            camera.viewportHeight
        );
        String time = App.getLoggedInUser().getLastGame().getGameTimer().getFormattedTime();
        String playerLevel = "level: " + App.getLoggedInUser().getLastGame().getPlayer().getLevel();
        String ammo = "ammo: " + App.getLoggedInUser().getLastGame().getPlayer().getWeapon().getAmmo() + "/" +
            App.getLoggedInUser().getLastGame().getPlayer().getMaxAmmo();
        String kill = "kill: " + App.getLoggedInUser().getLastGame().getPlayer().getKillNumber();

        float camX = camera.position.x - camera.viewportWidth / 2;
        float camY = camera.position.y + camera.viewportHeight / 2;
        for (int i = 0; i < App.getLoggedInUser().getLastGame().getPlayer().getHealth(); i++) {
            Main.getInstance().getBatch().draw(this.heart, camX + 10 + i * 30, camY - 40, 24, 24);
        }
        font.draw(Main.getInstance().getBatch(), time, camX + 40, camY - 60);
        Main.getInstance().getBatch().draw(this.ammo, camX + 10, camY - 110, 24, 24);
        font.draw(Main.getInstance().getBatch(), ammo, camX + 40, camY - 90);
        Main.getInstance().getBatch().draw(this.kill, camX + 10, camY - 140, 24, 24);
        font.draw(Main.getInstance().getBatch(), kill, camX + 40, camY - 120);
        font.draw(Main.getInstance().getBatch(), playerLevel, camX + 40, camY - 150);
        XPBar.setRange(0, App.getLoggedInUser().getLastGame().getPlayer().getLevel() * 20);
        XPBar.setPosition(camX + 40, camY - 250);
        XPBar.setValue(App.getLoggedInUser().getLastGame().getPlayer().getXP());
        XPBar.draw(Main.getInstance().getBatch(), 1f);
    }
}
