package io.github.some_example_name.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import io.github.some_example_name.Main;
import io.github.some_example_name.model.*;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class WeaponController {
    private final List<Bullet> bullets = new ArrayList<>();

    public WeaponController() {
    }

    public void update() {
        Weapon weapon = App.getLoggedInUser().getLastGame().getPlayer().getWeapon();
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        User user = App.getLoggedInUser();
        weapon.getWeaponSprite().setPosition(player.getX(), player.getY());
        weapon.getWeaponSprite().draw(Main.getInstance().getBatch());
        if (weapon.getAmmo() <= 0 && user.isAutoReloadingEnable()){
            player.setLastReloadWeapon(App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime());
            player.resetAmmo();
            if (App.isEnableSFX()) GameAsset.reloadGun.play(1f);
        }
        Gdx.input.setCursorCatched(App.getLoggedInUser().getLastGame().isAutoAimEnable());
        if (weapon.getAmmo() > 0 && App.getLoggedInUser().getLastGame().isAutoAimEnable()){
            if (App.getLoggedInUser().getLastGame().getLastAutoShoot() <= 0){
                autoAim();
                App.getLoggedInUser().getLastGame().setLastAutoShoot(1f);
            }
            else {
                App.getLoggedInUser().getLastGame().setLastAutoShoot(App.getLoggedInUser().getLastGame().getLastAutoShoot() - Gdx.graphics.getDeltaTime());
            }
        }
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
        if (App.isEnableSFX()) GameAsset.singleShoot.play(1f);
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

    private void autoAim(){
        Enemy enemy = nearestEnemy();
        if (enemy == null) return;
        Main.getInstance().getBatch().draw(GameAsset.cursor,enemy.getX(),enemy.getY());
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        Weapon weapon = player.getWeapon();
        for (int i = 0; i < player.getProjectile(); i++) {
            bullets.add(new Bullet(enemy.getX(), enemy.getY(), (int) weapon.getWeaponSprite().getX(), (int) weapon.getWeaponSprite().getY(), weapon, player, false));
        }
        weapon.setAmmo(weapon.getAmmo() - 1);
        if (App.isEnableSFX()) GameAsset.singleShoot.play(1f);
    }

    private Enemy nearestEnemy(){
        Player player = App.getLoggedInUser().getLastGame().getPlayer();
        int minDistance;
        Enemy enemyFirst = App.getLoggedInUser().getLastGame().getEnemies().get(0);
        if (enemyFirst!=null){
            minDistance = (int) Math.sqrt(Math.pow(player.getX() - enemyFirst.getX(),2) + Math.pow(player.getY() - enemyFirst.getY(),2));
        }
        else {
            return null;
        }
        for (Enemy enemy : App.getLoggedInUser().getLastGame().getEnemies()) {
            int distance = (int) Math.sqrt(Math.pow(player.getX() - enemy.getX(),2) + Math.pow(player.getY() - enemy.getY(),2));
            if (distance < minDistance){
                enemyFirst = enemy;
                minDistance = distance;
            }
        }
        return enemyFirst;
    }

    private void handleCollision(Bullet bullet) {
        for (Enemy enemy : App.getLoggedInUser().getLastGame().getEnemies()) {
            if (bullet.getCollisionRectangle().collidesWith(enemy.getCollisionRectangle())) {
                enemy.setHealth(enemy.getHealth() - bullet.getDamage());
                bullet.setDestroyed(true);
                if (enemy.getHealth() <= 0 && !enemy.isDying()) {
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
