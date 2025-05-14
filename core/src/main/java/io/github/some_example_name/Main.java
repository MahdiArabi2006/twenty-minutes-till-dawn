package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.view.FirstMenu;
import io.github.some_example_name.view.RegisterMenu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void create() {
        instance = this;
        batch = new SpriteBatch();
        instance.setScreen(FirstMenu.getInstance());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
