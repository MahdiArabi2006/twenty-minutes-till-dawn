package io.github.some_example_name.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.some_example_name.Main;
import io.github.some_example_name.controller.FirstMenuController;
import io.github.some_example_name.model.GameAsset;
import io.github.some_example_name.model.LanguageManager;
import io.github.some_example_name.model.TextKey;

import java.util.ArrayList;
import java.util.List;

public class FirstMenu extends Menu {
    private final List<TextButton> textButtonList = new ArrayList<>();
    private final Stage stage;
    private final Table table;
    private final Image background;
    private final FirstMenuController controller = new FirstMenuController(this);

    public FirstMenu() {
        this.stage = new Stage(Main.getInstance().getViewport(), Main.getInstance().getBatch());
        this.table = new Table(GameAsset.getMenuSkin());
        Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.background = new Image(backgroundTexture);
        textButtonList.add(new TextButton(LanguageManager.get(TextKey.MENU_LOGIN_TEXT_BUTTON), GameAsset.getMenuSkin()));
        textButtonList.add(new TextButton(LanguageManager.get(TextKey.MENU_SIGNUP_TEXT_BUTTON), GameAsset.getMenuSkin()));
        textButtonList.add(new TextButton(LanguageManager.get(TextKey.MENU_EXIT_GAME_TEXT_BUTTON), GameAsset.getMenuSkin()));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.clear();
        table.align(Align.bottomLeft);
        table.setFillParent(true);
        table.add(textButtonList.get(0)).padRight(40);
        table.add(textButtonList.get(1)).padLeft(50);
        table.row();
        table.add(textButtonList.get(2)).colspan(2).center().padTop(20);

        stage.clear();
        background.setFillParent(true);
        stage.addActor(background);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        controller.handleInput();
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

    public List<TextButton> getTextButtonList() {
        return textButtonList;
    }
}
