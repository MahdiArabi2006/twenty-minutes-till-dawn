package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tree implements Enemy{
    private int x;
    private int y;
    private float time;
    private float health;
    private final Texture treeTexture;
    private final CollisionRectangle collisionRectangle;
    private final Sprite Sprite;

    public Tree(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = 5;
        this.treeTexture = GameAsset.treeTexture;
        this.Sprite = new Sprite(treeTexture);
        this.Sprite.setPosition(x,y);
        this.collisionRectangle = new CollisionRectangle(this.x,this.y,this.Sprite.getWidth(),this.Sprite.getHeight());
    }

    public Texture getTreeTexture() {
        return treeTexture;
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

    public int getWidth(){
        return this.treeTexture.getWidth();
    }

    public int getHeight(){
        return this.treeTexture.getHeight();
    }

    public Sprite getSprite() {
        return Sprite;
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
}
