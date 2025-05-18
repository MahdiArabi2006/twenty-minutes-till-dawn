package io.github.some_example_name.model;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class LightEffect {
    private transient final Texture lightTexture;
    private final float radius;

    public LightEffect(float radius) {
        this.radius = radius;
        this.lightTexture = createLightTexture((int) (radius * 2));
    }

    private Texture createLightTexture(int size) {
        Pixmap pixmap = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                float dist = Vector2.dst(x, y, size / 2f, size / 2f) / (size / 2f);
                float alpha = 1 - MathUtils.clamp(dist, 0, 1);
                pixmap.setColor(1, 1, 1, alpha * 0.7f);
                pixmap.drawPixel(x, y);
            }
        }
        return new Texture(pixmap);
    }

    public void render(SpriteBatch batch, float x, float y) {
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        batch.draw(lightTexture,
            x - radius, y - radius,
            radius * 2, radius * 2);
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void dispose() {
        lightTexture.dispose();
    }
}
