package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.view.FirstMenu;
import io.github.some_example_name.view.GameView;
import io.github.some_example_name.view.RegisterMenu;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private SpriteBatch batch;
    private static Main instance;
    private OrthographicCamera camera;
    private Viewport viewport;


    public static Main getInstance() {
        return instance;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void create() {
        instance = this;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera); // اندازه مناسب برای بازی
        viewport.apply(); // فعال‌سازی ویوپورت
        batch = new SpriteBatch();
        instance.setScreen(new FirstMenu());
    }

    @Override
    public void render() {
        camera.update();
        viewport.apply(); // حتماً اعمال بشه
        batch.setProjectionMatrix(camera.combined);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
