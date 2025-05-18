package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tree implements Enemy {
    private int x;
    private int y;
    private float time;
    private float health;
    private boolean isDying = false;
    private float deathTimer = 0f;
    private final float deathDuration = 0.5f;
    private transient CollisionRectangle collisionRectangle;
    private transient Sprite sprite;

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 5;
        this.sprite = new Sprite(GameAsset.treeTexture);
        this.sprite.setPosition(x, y);
        this.collisionRectangle = new CollisionRectangle(this.x, this.y, this.sprite.getWidth(), this.sprite.getHeight());
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

    public int getWidth() {
        return (int) this.sprite.getWidth();
    }

    public int getHeight() {
        return (int) this.sprite.getHeight();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    @Override
    public Animation<Texture> getWalk() {
        return null;
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

    public float getDeathTimer() {
        return deathTimer;
    }

    public void setDeathTimer(float deathTimer) {
        this.deathTimer = deathTimer;
    }

    public boolean isDying() {
        return isDying;
    }

    public void setDying(boolean dying) {
        isDying = dying;
    }

    public float getDeathDuration() {
        return deathDuration;
    }

    public void initialAfterLoad(){
        this.sprite = new Sprite(GameAsset.treeTexture);
        this.sprite.setPosition(this.x, this.y);
        this.collisionRectangle = new CollisionRectangle(this.x, this.y,
            sprite.getWidth(), sprite.getHeight());
    }
}
