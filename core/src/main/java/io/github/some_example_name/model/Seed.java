package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Seed {
    private final CollisionRectangle collisionRectangle;
    private final Sprite sprite;
    private boolean isPicked;

    public Seed(float x, float y) {
        this.sprite = new Sprite(new Texture("enemy/T_EyeBat_EM_1.png"));
        this.sprite.setPosition(x, y);
        this.collisionRectangle = new CollisionRectangle(x, y, (float) (this.sprite.getWidth() * 0.5), (float) (this.sprite.getHeight() * 0.5));
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
}
