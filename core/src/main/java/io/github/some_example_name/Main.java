package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.some_example_name.model.App;
import io.github.some_example_name.save.SQLiteDB;
import io.github.some_example_name.view.FirstMenu;

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

    public void centerCameraOnPlayer(float playerX, float playerY) {
        camera.position.set(playerX, playerY, 0);
        camera.update();
    }

    @Override
    public void create() {
        instance = this;
        SQLiteDB.initialize();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();
        batch = new SpriteBatch();
        if (App.isEnableMusic()){
            if (App.getPlayedMusic() != null) App.getPlayedMusic().play();
        }
        instance.setScreen(new FirstMenu());
    }

    @Override
    public void render() {
        camera.update();
        viewport.apply();
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
        viewport.update(width, height, true);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
