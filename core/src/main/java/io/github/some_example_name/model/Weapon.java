package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Weapon {
    private final WeaponType weaponType;
    private Sprite weaponSprite;
    private int ammo;

    public Weapon(WeaponType weaponType) {
        this.weaponType = weaponType;
        this.weaponSprite = new Sprite(weaponType.getWeaponTexture());
        this.ammo = weaponType.getAmmoMax();
        weaponSprite.setX((float) Gdx.graphics.getWidth() / 2);
        weaponSprite.setY((float) Gdx.graphics.getHeight() / 2);
        weaponSprite.setSize(50, 50);
    }

    public Sprite getWeaponSprite() {
        return weaponSprite;
    }

    public void setWeaponSprite(Sprite weaponSprite) {
        this.weaponSprite = weaponSprite;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
