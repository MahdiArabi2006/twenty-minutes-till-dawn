package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Bullet;
import io.github.some_example_name.model.Weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponController {
    private List<Bullet> bullets = new ArrayList<>();

    public WeaponController() {
    }

    public void update() {
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        weapon.getWeaponSprite().draw(Main.getInstance().getBatch());
        updateBullets();
    }

    public void handleWeaponRotation(int x, int y) {
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        Sprite weaponSprite = weapon.getWeaponSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleWeaponShoot(int x, int y) {
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        bullets.add(new Bullet(x, y));
        weapon.setAmmo(weapon.getAmmo() - 1);
    }

    public void updateBullets() {
        for (Bullet b : bullets) {
            b.getSprite().draw(Main.getInstance().getBatch());
            Vector2 direction = new Vector2(
                Gdx.graphics.getWidth() / 2f - b.getX(),
                Gdx.graphics.getHeight() / 2f - b.getY()
            ).nor();

            b.getSprite().setX(b.getSprite().getX() - direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
        }
    }
}
