package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bullet {
    private Sprite sprite;
    private int damage = 5;
    private int x;
    private int y;
    private final int x_weapon;
    private final int y_weapon;

    public Bullet(int x, int y,int x_weapon,int y_weapon,Weapon weapon) {
        this.x = x;
        this.y = y;
        this.sprite = new Sprite(weapon.getWeaponType().getBulletTexture());
        sprite.setSize(20, 20);
        sprite.setX(weapon.getWeaponSprite().getX());
        sprite.setY(weapon.getWeaponSprite().getY());
        this.x_weapon = x_weapon;
        this.y_weapon = y_weapon;
    }

    public boolean isOutOfScreen(){
        return this.getSprite().getX() < 0 || this.getSprite().getX() > Gdx.graphics.getWidth() ||
            this.getSprite().getY() < 0 || this.getSprite().getY() > Gdx.graphics.getHeight();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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
}
