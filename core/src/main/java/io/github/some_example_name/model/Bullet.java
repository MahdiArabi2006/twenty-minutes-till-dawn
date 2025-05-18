package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {
    private transient Sprite sprite;
    private transient CollisionRectangle collisionRectangle;
    private final int damage;
    private boolean isDestroyed;
    private int x;
    private int y;
    private final Weapon weapon;
    private final int x_weapon;
    private final int y_weapon;

    public Bullet(int x, int y, int x_weapon, int y_weapon, Weapon weapon, Player player, boolean isEyeBatShoot) {
        this.x = x;
        this.y = y;
        this.weapon = weapon;
        if (isEyeBatShoot) {
            this.damage = 1;
        } else {
            this.damage = player.getDamage();
        }
        this.sprite = new Sprite(weapon.getWeaponType().getBulletTexture());
        sprite.setSize(20, 20);
        sprite.setX(weapon.getWeaponSprite().getX());
        sprite.setY(weapon.getWeaponSprite().getY());
        this.collisionRectangle = new CollisionRectangle(this.x, this.y, this.sprite.getWidth(), this.sprite.getHeight());
        this.x_weapon = x_weapon;
        this.y_weapon = y_weapon;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX_weapon() {
        return x_weapon;
    }

    public int getY_weapon() {
        return y_weapon;
    }

    public CollisionRectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public void initialAfterLoad(){
        this.weapon.initialAfterLoad();
        this.sprite = new Sprite(weapon.getWeaponType().getBulletTexture());
        sprite.setSize(20, 20);
        sprite.setX(weapon.getWeaponSprite().getX());
        sprite.setY(weapon.getWeaponSprite().getY());
        this.collisionRectangle = new CollisionRectangle(this.x, this.y, this.sprite.getWidth(), this.sprite.getHeight());
    }
}
