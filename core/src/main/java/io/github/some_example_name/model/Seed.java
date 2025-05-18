package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Seed {
    private float x;
    private float y;
    private transient CollisionRectangle collisionRectangle;
    private transient Sprite sprite;
    private boolean isPicked;

    public Seed(float x, float y) {
        this.x = x;
        this.y = y;
        this.sprite = new Sprite(new Texture("enemy/T_EyeBat_EM_1.png"));
        this.sprite.setPosition(x, y);
        this.collisionRectangle = new CollisionRectangle(x, y, this.sprite.getWidth(), this.sprite.getHeight());
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public CollisionRectangle getCollisionRectangle() {
        return collisionRectangle;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }

    public void initialAfterLoad(){
        this.sprite = new Sprite(new Texture("enemy/T_EyeBat_EM_1.png"));
        this.sprite.setPosition(x, y);
        this.collisionRectangle = new CollisionRectangle(x, y, this.sprite.getWidth(), this.sprite.getHeight());
    }
}
