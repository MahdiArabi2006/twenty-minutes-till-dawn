package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;

public enum Ability {
    VITALITY("vitality",new Texture("ability/Icon_Vitality.png")),
    DAMAGER("damager",new Texture("ability/T_LunaBlackHoleDamage_0.png")),
    PROJECTILE_INCREASE("projectile increase",new Texture("ability/T_ClusterProjectile.png")),
    AMMO_INCREASE("ammo increase",new Texture("ability/T_AmmoIcon.png")),
    SPEEDY("speedy",new Texture("ability/Icon_BlazingSpeed.png"));

    private final String name;
    private final Texture texture;

    Ability(String name,Texture texture) {
        this.texture = texture;
        this.name = name;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }
}
