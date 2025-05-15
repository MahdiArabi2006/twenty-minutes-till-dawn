package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;

public enum WeaponType {
    REVOLVER("revolver", 20, 1, 1, 6, GameAsset.revolverTexture, GameAsset.bulletTexture),
    SHOTGUN("shotgun", 10, 4, 1, 2, GameAsset.shotgunTexture, GameAsset.bulletTexture),
    SMG_DUAL("SMGs dual", 8, 1, 2, 24, GameAsset.SMGsTexture, GameAsset.bulletTexture);

    private final String name;
    private final int damage;
    private final int projectile;
    private final int timeReload;
    private final int ammoMax;
    private final Texture weaponTexture;
    private final Texture bulletTexture;

    WeaponType(String name, int damage, int projectile, int timeReload, int ammoMax, Texture weaponTexture, Texture bulletTexture) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        this.ammoMax = ammoMax;
        this.weaponTexture = weaponTexture;
        this.bulletTexture = bulletTexture;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectile() {
        return projectile;
    }

    public int getTimeReload() {
        return timeReload;
    }

    public int getAmmoMax() {
        return ammoMax;
    }

    public Texture getWeaponTexture() {
        return weaponTexture;
    }

    public Texture getBulletTexture() {
        return bulletTexture;
    }
}
