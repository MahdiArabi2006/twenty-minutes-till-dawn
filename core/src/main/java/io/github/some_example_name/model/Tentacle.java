package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tentacle implements Enemy {
    private transient Animation<Texture> textureWalk;
    private transient Sprite sprite;
    private transient CollisionRectangle collisionRectangle;
    private boolean isDying = false;
    private float deathTimer = 0f;
    private final float deathDuration = 0.5f;
    private float time;
    private int x;
    private int y;
    private final int HP;
    private float health;

    public Tentacle(int x, int y) {
        this.x = x;
        this.y = y;
        this.textureWalk = GameAsset.tentacleWalk;
        this.sprite = new Sprite(new Texture("enemy/tentacle/walk/T_TentacleEnemy_0.png"));
        this.sprite.setPosition(x, y);
        this.collisionRectangle = new CollisionRectangle(this.x, this.y, this.sprite.getWidth(), this.sprite.getHeight());
        this.HP = 25;
        this.health = this.HP;
    }

    public Animation<Texture> getTextureWalk() {
        return textureWalk;
    }

    public int getHP() {
        return HP;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public float getTime() {
        return this.time;
    }

    @Override
    public void setTime(float time) {
        this.time = time;
    }

    @Override
    public Animation<Texture> getWalk() {
        return this.textureWalk;
    }

    public CollisionRectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isDying() {
        return isDying;
    }

    public void setDying(boolean dying) {
        isDying = dying;
    }

    public float getDeathTimer() {
        return deathTimer;
    }

    public void setDeathTimer(float deathTimer) {
        this.deathTimer = deathTimer;
    }

    public float getDeathDuration() {
        return deathDuration;
    }

    public void initialAfterLoad(){
        this.sprite = new Sprite(new Texture("enemy/tentacle/walk/T_TentacleEnemy_0.png"));
        this.sprite.setPosition(this.x, this.y);
        this.textureWalk = GameAsset.tentacleWalk;
        this.collisionRectangle = new CollisionRectangle(this.x, this.y,
            sprite.getWidth(), sprite.getHeight());
    }
}
