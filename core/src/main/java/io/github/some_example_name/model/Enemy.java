package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public interface Enemy {
    int getX();

    void setX(int x);

    int getY();

    void setY(int y);

    int getWidth();

    int getHeight();

    Sprite getSprite();

    float getTime();

    void setTime(float time);

    Animation<Texture> getWalk();

    CollisionRectangle getCollisionRectangle();

    float getHealth();

    void setHealth(float health);

    float getDeathTimer();

    void setDeathTimer(float deathTimer);

    boolean isDying();

    void setDying(boolean dying);

    float getDeathDuration();

    void initialAfterLoad();
}
