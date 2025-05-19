package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.AfterGameController;
import io.github.some_example_name.model.App;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.LanguageManager;
import io.github.some_example_name.model.TextKey;

public class AfterGameView implements Screen {
    private final AfterGameController controller = new AfterGameController();
    private final Stage stage;
    private final TextButton backButton;
    private final Table table;
    private final Image background;
    private final boolean win;

    public AfterGameView(boolean win) {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table();
        this.win = win;
        Texture backgroundTexture;
        if (!win) {
            if (App.isEnableSFX()) GameAsset.lose.play(1f);
            backgroundTexture = new Texture(Gdx.files.internal("download.jpeg"));
        } else {
            if (App.isEnableSFX()) GameAsset.win.play(1f);
            backgroundTexture = new Texture(Gdx.files.internal("download (1).jpeg"));
        }
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        this.backButton = new TextButton(LanguageManager.get(TextKey.MENU_BACK_TEXT_BUTTON), GameAsset.getMenuSkin());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    private void setupUI() {
        table.clear();
        table.setFillParent(true);
        table.defaults().pad(10).width(300).height(40);
        table.align(Align.topLeft);
        if (win) {
            table.add(new Label(LanguageManager.get(TextKey.MENU_WIN_LABEL), GameAsset.getMenuSkin())).row();
        } else {
            table.add(new Label(LanguageManager.get(TextKey.MENU_LOSE_LABEL), GameAsset.getMenuSkin())).row();
        }
        table.add(new Label(LanguageManager.get(TextKey.MENU_USERNAME_LABEL) + ": " + App.getLoggedInUser().getUsername(), GameAsset.getMenuSkin())).row();
        int surviveTime = (int) (App.getLoggedInUser().getLastGame().getGameTimer().getCountdownDuration() - App.getLoggedInUser().getLastGame().getGameTimer().getRemainingTime());
        table.add(new Label(LanguageManager.get(TextKey.MENU_SURVIVAL_LABEL) + ": " + surviveTime / 60 + ":" + surviveTime % 60, GameAsset.getMenuSkin())).row();
        int kill = App.getLoggedInUser().getLastGame().getPlayer().getKillNumber();
        table.add(new Label(LanguageManager.get(TextKey.MENU_KILL_LABEL) + ": " + kill, GameAsset.getMenuSkin())).row();
        int score = surviveTime * kill;
        table.add(new Label(LanguageManager.get(TextKey.MENU_SCORE_LABEL) + ": " + score, GameAsset.getMenuSkin())).row();

        table.add(backButton).align(Align.center).width(250).height(100);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (App.isEnableSFX()) GameAsset.UIClick.play(1f);
                controller.afterGme(score, surviveTime, kill);
            }
        });

        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {
        if (stage!=null) stage.dispose();
    }
}
