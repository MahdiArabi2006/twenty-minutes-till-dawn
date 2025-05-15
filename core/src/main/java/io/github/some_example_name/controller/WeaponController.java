package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.Bullet;
import io.github.some_example_name.model.Player;
import io.github.some_example_name.model.Weapon;

import java.util.ArrayList;
import java.util.List;

public class WeaponController {
    private List<Bullet> bullets = new ArrayList<>();

    public WeaponController() {
    }

    public void update() {
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        weapon.getWeaponSprite().setPosition(player.getX(), player.getY());
        weapon.getWeaponSprite().draw(Main.getInstance().getBatch());
        updateBullets();
    }

    public void handleWeaponRotation(int x, int y) {
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        Sprite weaponSprite = weapon.getWeaponSprite();

        float weaponCenterX = weaponSprite.getX();
        float weaponCenterY = weaponSprite.getY();
        Vector3 worldCoords = new Vector3(x, y, 0);
        Main.getInstance().getViewport().unproject(worldCoords);
        float angle = (float) Math.atan2(worldCoords.y - weaponCenterY, worldCoords.x - weaponCenterX);

        weaponSprite.setRotation(angle * MathUtils.radiansToDegrees);
    }

    public void handleWeaponShoot(int x, int y) {
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        if (weapon.getAmmo() <= 0 || !finishReloading()){
            return;
        }
        Vector3 worldCoords = new Vector3(x, y, 0);
        Main.getInstance().getViewport().unproject(worldCoords);
        bullets.add(new Bullet((int) worldCoords.x, (int) worldCoords.y,weapon));
        weapon.setAmmo(weapon.getAmmo() - 1);
    }

    public void updateBullets() {
        for (Bullet b : bullets) {
            b.getSprite().draw(Main.getInstance().getBatch());
            Vector2 direction = new Vector2(
                 b.getX() - b.getX_weapon(),
                 b.getY() - b.getY_weapon()
            ).nor();

            b.getSprite().setX(b.getSprite().getX() + direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 5);
        }
    }

    private boolean finishReloading(){
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        float now = App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime();
        return Math.abs(now - player.getLastReloadWeapon()) >= weapon.getWeaponType().getTimeReload();
    }
}
