package io.github.some_example_name.controller;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;

import java.util.ArrayList;
import java.util.Iterator;
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
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        if (weapon.getAmmo() <= 0 || !finishReloading()) {
            return;
        }
        Vector3 worldCoords = new Vector3(x, y, 0);
        Main.getInstance().getViewport().unproject(worldCoords);
        for (int i = 0; i < player.getProjectile(); i++) {
            bullets.add(new Bullet((int) worldCoords.x, (int) worldCoords.y, (int) weapon.getWeaponSprite().getX(), (int) weapon.getWeaponSprite().getY(), weapon, player, false));
        }
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
            b.getCollisionRectangle().move(b.getSprite().getX(), b.getSprite().getY());
            handleCollision(b);
        }
        bullets.removeIf(Bullet::isDestroyed);
    }

    private void handleCollision(Bullet bullet) {
        for (Enemy enemy : App.getLoggedInUser().getLastGame().getEnemies()) {
            if (bullet.getCollisionRectangle().collidesWith(enemy.getCollisionRectangle())) {
                enemy.setHealth(enemy.getHealth() - bullet.getDamage());
                bullet.setDestroyed(true);
                if (enemy.getHealth() <= 0) {
                    enemy.setDying(true);
                    enemy.setDeathTimer(enemy.getDeathDuration());
                    App.getLoggedInUser().getLastGame().getPlayer().setKillNumber(App.getLoggedInUser().getLastGame().getPlayer().getKillNumber() + 1);
                }
            }
        }
    }

    private boolean finishReloading() {
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        float now = App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime();
        return Math.abs(now - player.getLastReloadWeapon()) >= weapon.getWeaponType().getTimeReload();
    }
}
