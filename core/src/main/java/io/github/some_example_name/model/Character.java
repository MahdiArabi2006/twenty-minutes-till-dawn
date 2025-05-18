package io.github.some_example_name.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public enum Character {
    SHANA("shana", 4, 4, GameAsset.shanaWalk, GameAsset.shanaIdle, new Image(new Texture(Gdx.files.internal("shana.png"))), new Sprite(new Texture("Idle/shana/0.png"))),
    SCARLETT("scarlett", 5, 3, GameAsset.scarletWalk, GameAsset.scarletIdle, new Image(new Texture(Gdx.files.internal("scarlett.png"))), new Sprite(new Texture("Idle/scarlet/0.png"))),
    DIAMOND("diamond", 1, 7, GameAsset.diamondWalk, GameAsset.diamondIdle, new Image(new Texture(Gdx.files.internal("diamond.png"))), new Sprite(new Texture("Idle/diamond/0.png"))),
    DASHER("dasher", 10, 2, GameAsset.dasherWalk, GameAsset.dasherIdle, new Image(new Texture(Gdx.files.internal("dasher.png"))), new Sprite(new Texture("Idle/dasher/0.png"))),
    LILITH("lilith", 3, 5, GameAsset.lilithWalk, GameAsset.lilithIdle, new Image(new Texture(Gdx.files.internal("lilith.png"))), new Sprite(new Texture("Idle/lilith/0.png")));

    private final String name;
    private final int speed;
    private final int HP;
    private transient final Animation<Texture> walk;
    private transient final Animation<Texture> idle;
    private transient final Sprite sprite;
    private final Image avatar;

    Character(String name, int speed, int HP, Animation<Texture> walk, Animation<Texture> idle, Image avatar, Sprite sprite) {
        this.name = name;
        this.speed = speed;
        this.HP = HP;
        this.walk = walk;
        this.idle = idle;
        this.avatar = avatar;
        this.sprite = sprite;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHP() {
        return HP;
    }

    public Animation<Texture> getWalk() {
        return walk;
    }

    public Animation<Texture> getIdle() {
        return idle;
    }

    public Image getAvatar() {
        return avatar;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
